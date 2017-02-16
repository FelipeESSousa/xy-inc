package br.com.zup.web.bean;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "serviceResponse")
public class ServiceResponse
{

    private int code;

    private String status;

    private String description;

    public ServiceResponse()
    {
    }

    public ServiceResponse(int code, String status, String description)
    {
        this.code = code;
        this.status = status;
        this.description = description;
    }

    /**
     * @return the code
     */
    @XmlElement
    public int getCode()
    {
        return code;
    }

    /**
     * @param code
     *            the code to set
     */
    public void setCode(int code)
    {
        this.code = code;
    }

    /**
     * @return the status
     */
    @XmlElement
    public String getStatus()
    {
        return status;
    }

    /**
     * @param status
     *            the status to set
     */
    public void setStatus(String status)
    {
        this.status = status;
    }

    /**
     * @return the description
     */
    @XmlElement
    public String getDescription()
    {
        return description;
    }

    /**
     * @param description
     *            the description to set
     */
    public void setDescription(String description)
    {
        this.description = description;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + code;
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((status == null) ? 0 : status.hashCode());
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        ServiceResponse other = (ServiceResponse) obj;
        if (code != other.code) {
            return false;
        }
        if (description == null) {
            if (other.description != null) {
                return false;
            }
        } else if (!description.equals(other.description)) {
            return false;
        }
        if (status == null) {
            if (other.status != null) {
                return false;
            }
        } else if (!status.equals(other.status)) {
            return false;
        }
        return true;
    }

}
