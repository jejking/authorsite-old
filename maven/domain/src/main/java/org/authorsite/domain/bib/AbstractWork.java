package org.authorsite.domain.bib;

import org.authorsite.domain.AbstractEntry;


/**
 * Class representing common features of works to be 
 * captured in the bibliographic sub-system of the authorsite 
 * application.
 * 
 * <p>All works are held to have the following additional
 * properties to {@link AbstractEntry}:</p>
 * <ul>
 * <li><code>Title</code>: the title of the work. This may be "Unknown".</li>
 * <li><code>WorkDates</code>: the dates of the work. See {@link WorkDates}.</li> 
 * </ul>
 * 
 * 
 * @author jejking
 *
 */
public abstract class AbstractWork extends AbstractEntry {

    private String title;
    private WorkDates workDates = new WorkDates();
    
    /**
     * Default constructor.
     */
    public AbstractWork() {
        super();
    }


    /**
     * Gets title.
     * 
     * @return title. If <code>null</code>, returns "Unknown".
     */
    public String getTitle() {
	if ( title == null ) {
	    return "Unknown";
	}
	else {
	    return title;
	}
    }

    
    /**
     * Sets title.
     * 
     * @param title may be <code>null</code>.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    
    /**
     * Gets work dates.
     * 
     * @return work dates, may be <code>null</code> if not known.
     */
    public WorkDates getWorkDates() {
        return this.workDates;
    }

    
    /**
     * Sets work dates.
     * 
     * @param workDates may be <code>null</code>.
     */
    public void setYears(WorkDates workDates) {
        this.workDates = workDates;
    }
    
    
}
