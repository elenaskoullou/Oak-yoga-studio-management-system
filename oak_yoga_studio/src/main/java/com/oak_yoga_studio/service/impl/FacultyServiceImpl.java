/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oak_yoga_studio.service.impl;

import com.oak_yoga_studio.dao.SectionDAO;
import com.oak_yoga_studio.dao.CustomerDAO;
import com.oak_yoga_studio.dao.FacultyDAO;
import com.oak_yoga_studio.dao.WaiverDAO;
import com.oak_yoga_studio.domain.Customer;
import com.oak_yoga_studio.domain.Faculty;
import com.oak_yoga_studio.domain.Section;
import com.oak_yoga_studio.domain.Waiver;
import com.oak_yoga_studio.service.IFacultyService;
import com.oak_yoga_studio.service.INotificationService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Senai
 */
public class FacultyServiceImpl implements IFacultyService {
    
    private FacultyDAO facultyDAO;
    private SectionDAO sectionDAO;
    private WaiverDAO waiverDAO;
    private CustomerDAO customerDAO;
    private INotificationService notificationService;
    
    public FacultyServiceImpl(FacultyDAO facultyDAO, SectionDAO sectionDAO, WaiverDAO waiverDAO,
            CustomerDAO customerDAO, INotificationService notificationService) {
        this.facultyDAO = facultyDAO;
        this.sectionDAO = sectionDAO;
        this.waiverDAO = waiverDAO;
        this.customerDAO = customerDAO;
        this.notificationService = notificationService;
    }
    
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void addFaculty(Faculty faculty) {
        try {
            facultyDAO.addFaculty(faculty);
        } catch (Exception e) {
            
        }
    }
    
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateFaculty(int id, Faculty faculty) {
        try {
            Faculty f = facultyDAO.getFaculty(id);
            f.setFirstName(faculty.getFirstName());
            f.setLastName(faculty.getLastName());
            f.setDateOfBirth(faculty.getDateOfBirth());
            f.setEmail(faculty.getEmail());
            f.setProfilePicture(faculty.getProfilePicture());
            f.getCredential().setActive(faculty.getCredential().isActive());
            facultyDAO.updateFaculty(f);
        } catch (Exception ex) {
            System.out.println("Error:" + ex.getMessage());
        }
        
    }
    
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Faculty> getListOfActiveFaculty() {
        try {
            return facultyDAO.getAllActiveFaculties();
        } catch (Exception e) {
            return new ArrayList();
        }
        
    }
    
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Faculty> getListOfFaculty() {
        try {
            return facultyDAO.getAllFaculties();
        } catch (Exception e) {
            return new ArrayList();
        }
        
    }
    
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Faculty getFacultyById(int Id) {
        try {
            return facultyDAO.getFaculty(Id);
        } catch (Exception e) {
            return null;
        }
    }
    
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void enableOrdisableFaculty(Faculty faculty) {
        try {
            if (faculty.getCredential().isActive()) {
                faculty.getCredential().setActive(false);
            } else {
                faculty.getCredential().setActive(true);
            }
            facultyDAO.updateFaculty(faculty);
        } catch (Exception e) {
            
        }
    }
    
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Section> getFacultySections(Faculty faculty) {
        try {
            faculty = facultyDAO.getFaculty(faculty.getId());
            System.out.println("Section Size" + faculty.getSections().size());
            return faculty.getSections();
        } catch (Exception e) {
            return new ArrayList();
        }
    }
    
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateWaiverRequest(Waiver waiver) {
        try {
            waiverDAO.updateWaiver(waiver);
        } catch (Exception e) {
            
        }
    }
    
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Customer> getfacultyAdvisees(Faculty faculty) {
        try {
            faculty = facultyDAO.getFaculty(faculty.getId());
            System.out.println("Name is" + faculty.getFirstName());
            return faculty.getAdvisees();
        } catch (Exception e) {
            return new ArrayList();
        }
    }
    
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Waiver> getfacultyPendingWaiverRequests(Faculty faculty) {
        try {
            Faculty faculty1 = facultyDAO.getFaculty(faculty.getId());
            
            List<Customer> advisees = faculty1.getAdvisees();
            List<Waiver> waiverRequests = new ArrayList();
            
            for (Customer c : advisees) {
                List<Waiver> waivers = c.getWaivers();
                
                for (Waiver w : waivers) {
                    System.out.println("to get waiver 1 " + faculty1 + " " + w.getStatus());
                    if (w.getStatus().equals(Waiver.Status.PENDING)) {
                        waiverRequests.add(w);
                    }
                }
            }
            return waiverRequests;
        } catch (Exception e) {
            System.out.println("Exception " + e.getMessage());
            return new ArrayList();
        }
    }
    
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Waiver> getfacultyDecidedWaivers(Faculty faculty) {
        try {
            Faculty faculty1 = facultyDAO.getFaculty(faculty.getId());
            List<Customer> advisees = faculty1.getAdvisees();
            List<Waiver> decidedWaivers = new ArrayList();
            System.out.println("before advises");
            for (Customer c : advisees) {
                List<Waiver> waivers = c.getWaivers();                
                System.out.println("one advisee");
                for (Waiver w : waivers) {
                    System.out.println("waive");
                    if (!w.getStatus().equals(Waiver.Status.PENDING)) {
                        decidedWaivers.add(w);
                    }
                }
            }
            return decidedWaivers;
        } catch (Exception e) {
            System.out.println("Exception " + e.getMessage());
            return new ArrayList();
        }
    }
}
