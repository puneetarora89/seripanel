package com.seri.web.utils.tags;

import com.seri.web.dao.CountryDao;
import com.seri.web.dao.SchoolDao;
import com.seri.web.dao.daoImpl.CountryDaoImpl;
import com.seri.web.dao.daoImpl.SchoolDaoImpl;
import com.seri.web.model.Country;
import com.seri.web.model.School;
import com.seri.web.utils.GlobalFunUtils;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.util.List;

/**
 * Created by puneet on 21/05/16.
 */
public class CountrySelectorTag extends SimpleTagSupport {

    private String ctrlName;
    private String ctrlId;
    private String ctrlClass;
    private String selectedCountry;
    private GlobalFunUtils globalFunUtils = new GlobalFunUtils();
    CountryDao countryDao = new CountryDaoImpl();

    public void setCtrlName(String ctrlName) {
        this.ctrlName = ctrlName;
    }

    public void setCtrlId(String ctrlId) {
        this.ctrlId = ctrlId;
    }

    public void setCtrlClass(String ctrlClass) {
        this.ctrlClass = ctrlClass;
    }

    public void setSelectedCountry(String selectedCountry) {
        this.selectedCountry = selectedCountry;
    }

    public void doTag() throws IOException {
        if(ctrlClass==null || ctrlClass=="")
            ctrlClass="countriesList";

        if(ctrlId==null || ctrlId=="")
            ctrlId="countriesList";

        if(ctrlName==null || ctrlName=="")
            ctrlName="countriesList";

        if(selectedCountry==null || selectedCountry=="")
            selectedCountry="0";

        List<Country> countriesList = countryDao.getAllCountries();
        String selectCountriesListCtrl = "<select name='"+ctrlName+"' id='"+ctrlId+"' class='"+ctrlClass+"'>";
        if(countriesList != null) {
            selectCountriesListCtrl+= "<option value='0'>Make a selection</option>";
            for (Country country : countriesList) {
                selectCountriesListCtrl += "<option value='" + country.getCountryId() + "' "+((String.valueOf(country.getCountryId()).equals(selectedCountry))?"selected='selected'":"")+" data-id='"+country.getCountryCode()+"'>" + country.getCountryName() + "</option>";
            }
        } else {
            selectCountriesListCtrl+= "<option value='0'>-No Country Found in Records-</option>";
        }
        selectCountriesListCtrl += "</select>";
        JspWriter out = getJspContext().getOut();
        out.println(selectCountriesListCtrl);
    }

}
