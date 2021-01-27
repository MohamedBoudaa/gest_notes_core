package com.bll;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.bo.Etudiant;
import com.bo.InscriptionAdministrative;
import com.bo.InscriptionMatiere;
import com.bo.InscriptionModule;
import com.bo.InscriptionPedagogique;
import com.bo.Matiere;
import com.bo.Module;
import com.bo.Niveau;
import com.dao.DaoFactory;
import com.dao.impl.EtudiantDaoImpl;
import com.dao.impl.InscAdminDaoImpl;
import com.dao.impl.InscMatiereDaoImpl;
import com.dao.impl.InscModuleDaoImpl;
import com.dao.impl.InscPedagoDaoImpl;
import com.dao.impl.MatiereDaoImpl;
import com.dao.impl.ModuleDaoImpl;
import com.dao.impl.NiveauDaoImpl;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.VerticalPositionMark;  

public class PDFCreator {

	private static final EtudiantDaoImpl etuDAO = (EtudiantDaoImpl) DaoFactory.getDaoFactory().getDao(DaoFactory.DAO_ETUDIANT);
	private static final InscAdminDaoImpl inscAdminDAO = (InscAdminDaoImpl) DaoFactory.getDaoFactory().getDao(DaoFactory.DAO_INSCADMIN);
	private static final NiveauDaoImpl nivDAO= (NiveauDaoImpl) DaoFactory.getDaoFactory().getDao(DaoFactory.DAO_NIVEAU);
	private static final MatiereDaoImpl matDAO=(MatiereDaoImpl) DaoFactory.getDaoFactory().getDao(DaoFactory.DAO_MATIERE);
	private static final ModuleDaoImpl modDAO=(ModuleDaoImpl) DaoFactory.getDaoFactory().getDao(DaoFactory.DAO_MODULE);
	private static final InscPedagoDaoImpl inscPedagDAO=(InscPedagoDaoImpl) DaoFactory.getDaoFactory().getDao(DaoFactory.DAO_INSCPEDAGO);
	private static final InscModuleDaoImpl inscModuleDAO=(InscModuleDaoImpl) DaoFactory.getDaoFactory().getDao(DaoFactory.DAO_INSCMODULE);
	private static final InscMatiereDaoImpl inscMatiereDAO= (InscMatiereDaoImpl) DaoFactory.getDaoFactory().getDao(DaoFactory.DAO_INSCMATIERE);
	private static final SearchManagerImpl search=new SearchManagerImpl();
	
	private static ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
	private static String imgUae = classLoader.getResource("com/resources/uae.PNG").getPath();
	private static String imgEnsah = classLoader.getResource("com/resources/ensah.PNG").getPath();
	
	public static PdfPTable addHeader(){
        PdfPTable header = new PdfPTable(3);
        header.setSpacingAfter(50f);
        try {
        	header.setWidthPercentage(100);
            header.setTotalWidth(527);
            header.setLockedWidth(true);
          
            PdfPCell cell1 = new PdfPCell(Image.getInstance(imgUae));
            cell1.setFixedHeight(60);
            cell1.setBorder(Rectangle.NO_BORDER);
            cell1.setHorizontalAlignment(Element.ALIGN_LEFT);
            header.addCell(cell1);
            
            PdfPCell cell2 = new PdfPCell(new Phrase("Université Abdelmalek Essaadi\n"
            		+ "ENSAH- Ecole Nationale des Sciences Appliquées Al Hoceima"));
            cell2.setBorder(Rectangle.NO_BORDER);
            cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
            header.addCell(cell2);
            
            PdfPCell cell3 = new PdfPCell(Image.getInstance(imgEnsah));
            cell3.setFixedHeight(60);
            cell3.setBorder(Rectangle.NO_BORDER);
            cell3.setHorizontalAlignment(Element.ALIGN_RIGHT);
            
            header.addCell(cell3);
            
            header.completeRow();

        } catch(DocumentException de) {
            throw new ExceptionConverter(de);
        } catch (MalformedURLException e) {
            throw new ExceptionConverter(e);
        } catch (IOException e) {
            throw new ExceptionConverter(e);
        }
        return header;
    }
	
	public static PdfPTable addDetails(Etudiant etudiant, Niveau niveau,int year) {
		PdfPTable table = new PdfPTable(1);
        table.setWidthPercentage(100);
		table.setTotalWidth(527);
		table.setLockedWidth(true);
		table.setSpacingAfter(5f);
		
		PdfPCell cell= new PdfPCell( new Phrase("Bulletin annuel\n" +niveau.getLabel()+"\nAnnée universitaire : "+ year+"-"+year+1,
				new Font(Font.FontFamily.HELVETICA, 13)) );
		cell.setBorder(Rectangle.NO_BORDER);
    	cell.setFixedHeight(50);
    	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    	table.addCell(cell);
    	
    	Chunk g = new Chunk(new VerticalPositionMark());
    	Phrase p = new Phrase();
    	p.add(etudiant.getFirstname()+" "+etudiant.getSecondName());
    	p.add(g);
    	p.add(etudiant.getCne());
    	PdfPCell cell2=new PdfPCell();
    	cell2.setBorder(Rectangle.NO_BORDER);
    	cell2.addElement(p);
    	table.addCell(cell2);
    	
		return table;
	}

   	public static PdfPTable addBody(
			Etudiant etudiant, Niveau niveau, InscriptionAdministrative inscAdmin,InscriptionPedagogique inscPedag) {
		
		PdfPTable table = new PdfPTable(new float[] { 2,4,3,2 });

        table.setWidthPercentage(100);
		table.setTotalWidth(527);
		table.setLockedWidth(true);
		table.setSpacingAfter(15f);
        
		PdfPCell cell1=new PdfPCell( new Phrase("Modules"));
		PdfPCell cell2=new PdfPCell( new Phrase("Elements des modules"));
		PdfPCell cell3=new PdfPCell( new Phrase("Notes des élèments des modules /20"));
		PdfPCell cell4=new PdfPCell( new Phrase("Moyennes des modules /20"));
		
		cell1.setFixedHeight(40);
		
		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell4.setVerticalAlignment(Element.ALIGN_MIDDLE);
		
		table.addCell(cell1);
		table.addCell(cell2);
		table.addCell(cell3);
		table.addCell(cell4);
		
		
		List<Module> modules=modDAO.getModuleByNiveauOrderByS(niveau.getId());
		
		for(int i=0;i<modules.size();i++) {
			
			Long idM=modules.get(i).getId();
			List<Matiere> matieres=matDAO.getMatiereByModule(idM);
			List<InscriptionModule> inscModule =inscModuleDAO.getInscriptionModule(inscPedag.getId(),idM);
			
			if(!inscModule.isEmpty()) {
				//cell1=new PdfPCell( new Phrase(modules.get(i).getTitle()));
				cell1=new PdfPCell( new Phrase("M"+(i+1)));
				cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell1);
				
				PdfPTable t = new PdfPTable(1);
				for(int j=0;j<matieres.size();j++) {
					PdfPCell cell=new PdfPCell(new Phrase(matieres.get(j).getTitle()));
					t.addCell(cell);	
				}
				PdfPCell cell=new PdfPCell(t);
				table.addCell(cell);
				
				Long[] idModules=new Long[modules.size()];
				for(int j=0;j<modules.size();j++) {
					idModules[j]=modules.get(j).getId();
				}
										
				Long[] idMatieres=new Long[matieres.size()];
				for(int j=0;j<matieres.size();j++) {
					idMatieres[j]=matieres.get(j).getId();
				}
				
				List<InscriptionMatiere> inscMatieres=inscMatiereDAO.getInscriptionMatiere(inscModule.get(0).getId(),idMatieres);
				
				t = new PdfPTable(1);
				for(InscriptionMatiere it:inscMatieres) {
					cell=new PdfPCell(new Phrase(""+it.getNote().getNoteFinal()));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					t.addCell(cell);
				}
				cell=new PdfPCell(t);
				table.addCell(cell);
				
				t = new PdfPTable(1);
				for(InscriptionModule it1:inscModule) {
					cell=new PdfPCell(new Phrase(""+it1.getNote().getNoteFinal()));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					t.addCell(cell);
				}
				cell=new PdfPCell(t);
				table.addCell(cell);
				}
			
		}
		PdfPCell cell= new PdfPCell(new Phrase("Moyenne Générale"));
		cell.setFixedHeight(40);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setColspan(2);
        table.addCell(cell);
        
        double noteF=inscAdmin.getNoteFinal();
        cell = new PdfPCell(new Phrase(noteF+"/20"));
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
    	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        
        if(niveau.getCycle()==1) {
        	if(inscAdmin.getNoteFinal()>=10) {
        		 cell = new PdfPCell(new Phrase("Résultat : Admis"));
        		 cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        		 cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        	     table.addCell(cell);
        	}else {
        		 cell = new PdfPCell(new Phrase("Résultat : Non admis"));
        		 cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        		 cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        	     table.addCell(cell);
        	}
        }else if(niveau.getCycle()==2) {
        	if(inscAdmin.getNoteFinal()>=12) {
       		 	cell= new PdfPCell(new Phrase("Résultat : Admis"));
       			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
       			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
       		 	table.addCell(cell);
        	}else {
       		 	cell = new PdfPCell(new Phrase("Résultat : Non admis"));
       			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
       			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
       		 	table.addCell(cell);
        	}
       }
       
		return table;
	}
    public static  PdfPTable addFooter() {
    	
    	PdfPTable table = new PdfPTable(1);
        table.setWidthPercentage(100);
		table.setTotalWidth(527);
		table.setLockedWidth(true);
     	
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date date = new Date();
    	
    	PdfPCell cell=new PdfPCell(new Phrase("Fait le "+formatter.format(date)));
    	cell.setBorder(Rectangle.NO_BORDER);
    	cell.setFixedHeight(50);
    	table.addCell(cell);
    	
    	cell=new PdfPCell(new Phrase("important il ne peut être délivré qu'un seul exemplaire du présent relevé de notes aucun duplicat ne sera fourni."));
    	cell.setBorder(Rectangle.NO_BORDER);
    	cell.setFixedHeight(50);
    	table.addCell(cell);
    
    	return table;
    }
    
    public static File generatePDF
    (Etudiant etudiant, Niveau niveau, InscriptionAdministrative inscAdmin,InscriptionPedagogique inscPedag,int year ) {
    	
    	Document document = new Document();
    	File file=null;
		try {
			file=new File("bulletin"+etudiant.getCin()+year+niveau.getTitle()+".pdf");
			PdfWriter writer =PdfWriter.getInstance(document, new FileOutputStream(file));
			//PdfWriter writer =PdfWriter.getInstance(document, new FileOutputStream(directoryName+"\\bulletin"+etudiant.getCin()+".pdf"));
			document.open();
			PdfPTable header=PDFCreator.addHeader();
			document.add(header);
			PdfPTable details=PDFCreator.addDetails(etudiant, niveau,year);
			document.add(details);
			PdfPTable body=PDFCreator.addBody(etudiant, niveau, inscAdmin, inscPedag);
			document.add(body);
			PdfPTable footer=PDFCreator.addFooter();
			document.add(footer);
			document.close();
		} catch (FileNotFoundException | DocumentException e) {
				e.printStackTrace();
		}
		return file;
		
    }
}
