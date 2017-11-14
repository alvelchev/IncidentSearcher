package com.ds.beans;

import com.ds.entity.Incident;
import com.ds.beans.util.JsfUtil;
import com.ds.beans.util.JsfUtil.PersistAction;
import com.ds.facade.IncidentFacade;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

@Named("incidentController")
@SessionScoped
public class IncidentController implements Serializable {

    @Inject
    IncidentFacade ejbFacade;

    private List<Incident> items = null;
    private Incident selected;
    private String searchIncField;
    private Date startDateField;
    private Date endDateField;
    private String mainNote;
    private String resolveNote;
    private List<Incident> incList;

    public IncidentController() {
    }

    public void searchByIncNumber() {
        try {
            items = ejbFacade.findByIncNumber(searchIncField);
        } catch (NullPointerException e) {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "No data found for given criteria.",
                            "Please try again."));
        }
    }

    public void searchByMainNote() {
        items = ejbFacade.findByMainNote(mainNote);
        if (items.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "No data found for given criteria.",
                            "Please try again."));
        }
    }

    public void searchByResolveNote() {
        items = ejbFacade.findByResolveNote(resolveNote);
        if (items.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "No data found for given criteria.",
                            "Please try again."));
        }
    }

    public void searchByIncDate() {
        if (endDateField == null) {
            System.out.println("End date is null");

            System.out.println("Date: " + startDateField);
            items = ejbFacade.findByDate(startDateField);
            if (items.isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(
                        null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                "No data found for given criteria.",
                                "Please try again."));
            }
        } else {
            items = ejbFacade.findByDatePeriod(startDateField, endDateField);
            if (items.isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(
                        null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                "No data found for given criteria.",
                                "Please try again."));
            }

        }

    }

    public Incident getSelected() {
        return selected;
    }

    public void setSelected(Incident selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private IncidentFacade getFacade() {
        return ejbFacade;
    }

    public Incident prepareCreate() {
        selected = new Incident();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/msgs_en").getString("IncidentCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/msgs_en").getString("IncidentUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/msgs_en").getString("IncidentDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Incident> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/msgs_en").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/msgs_en").getString("PersistenceErrorOccured"));
            }
        }
    }

    public Incident getIncident(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<Incident> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Incident> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Incident.class)
    public static class IncidentControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            IncidentController controller = (IncidentController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "incidentController");
            return controller.getIncident(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Incident) {
                Incident o = (Incident) object;
                return getStringKey(o.getIncId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Incident.class.getName()});
                return null;
            }
        }

    }

    public String getSearchIncField() {
        return searchIncField;
    }

    public void setSearchIncField(String searchIncField) {
        this.searchIncField = searchIncField;
    }

    public List<Incident> getIncList() {
        return incList;
    }

    public void setIncList(List<Incident> incList) {
        this.incList = incList;
    }

    public Date getStartDateField() {
        return startDateField;
    }

    public void setStartDateField(Date startDateField) {
        this.startDateField = startDateField;
    }

    public Date getEndDateField() {
        return endDateField;
    }

    public void setEndDateField(Date endDateField) {
        this.endDateField = endDateField;
    }

    public String getMainNote() {
        return mainNote;
    }

    public void setMainNote(String mainNote) {
        this.mainNote = mainNote;
    }

    public String getResolveNote() {
        return resolveNote;
    }

    public void setResolveNote(String resolveNote) {
        this.resolveNote = resolveNote;
    }

}
