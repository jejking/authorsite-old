/**
 * 
 */
package org.juenger.wiki.item;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.StandardToStringStyle;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Bean like object encapsulating name and version information. This 
 * information can be used to identify an wiki item such as a {@link Page}
 * or {@link Attachment} uniquely. It can thus also be used in maps and sets.
 * 
 * <p>It is not a "true" bean in that there is no default constructor and no setter
 * methods.</p>
 * 
 * @author jejking
 */
public final class VersionedName implements Serializable, Comparable, Cloneable {

	/**
	 * Computed serial version UID (from Eclipse). 
	 */
	private static final long serialVersionUID = -5506507327961129380L;
	
	private static final StandardToStringStyle TO_STRING_STYLE = new StandardToStringStyle();
	static {
		TO_STRING_STYLE.setUseIdentityHashCode(false);
	}
	
	private String name;
	private int version;
	private transient int hashCode;
	private transient String toStringText;
	
	/**
     * Constructs a new object using the supplied parameters.
     * 
	 * @param name
	 * @param version must be zero or greater
	 */
	public VersionedName(String name, int version) {
		init(name, version);
	}

	/**
	 * Helper method used by constructor and {@link VersionedName#readObject(ObjectInputStream)}
	 * to assemble a sane object.
	 * 
	 * @param name
	 * @param version
	 */
	private void init(String name, int version) {
		//		 sanity checks
        if (name == null) {
            throw new IllegalArgumentException("name parameter is null");
        }
        if (name.length() == 0) {
            throw new IllegalArgumentException("name parameter is empty string");
        }
        if (version < 0) {
            throw new IllegalArgumentException("version parameter is less than zero");
        }
		this.name = name;
		this.version = version;
		this.hashCode = calculateHashCode();
		this.toStringText = buildToStringText();
		
	}


	/**
     * Returns name.
     * 
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
     * Returns version.
     * 
	 * @return version
	 */
	public int getVersion() {
		return version;
	}
    
    /**
     * Determines if the parameter passed in is the next version
     * of the versioned name -i.e. if its version number is equal
     * to the version number of this object + 1.
     * 
     * <p>If version 2 of a given versioned
     * name is passed in to version 1, it will be return true. 
     * If version 1 or a given versioned name is passed into 
     * version 2, it will return false.</p>
     * 
     * @param nameToCompare
     * @return
     */
    public boolean isNextVersion(VersionedName nameToCompare) {
        if (nameToCompare == null) {
            throw new IllegalArgumentException("name to compare parameter is null");
        }
        if (nameToCompare.getName().equals(name)) {
            if (nameToCompare.getVersion() == (version + 1) ) {
                return true;
            }
            else {
                return false;
            }
        }
        else {
            return false;
        }
    }
    
    public boolean isPreviousVersion(VersionedName nameToCompare) {
        if (nameToCompare == null) {
            throw new IllegalArgumentException("name to compare parameter is null");
        }
        if (nameToCompare.getName().equals(name)) {
            if (nameToCompare.getVersion() == (version - 1) ) {
                return true;
            }
            else {
                return false;
            } 
        }
        else {
            return false;
        }
    }

	@Override
	protected Object clone() {
		return new VersionedName(this.name, this.version);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (! (obj instanceof VersionedName)) {
			return false;
		}
		VersionedName rhs = (VersionedName) obj;
		if (this.name.equals(rhs.name)
				&& this.version == rhs.version) {
			return true;
		}
        return false;
	}

	@Override
	public int hashCode() {
		return this.hashCode;
	}

	@Override
	public String toString() {
		return this.toStringText;
	}

	public int compareTo(Object o) {
		if (o == null) {
			throw new NullPointerException("cannot compare to null");
		}
		if (o.equals(this)) {
			return 0;
		}
		if (! (o instanceof VersionedName)) {
			throw new ClassCastException("cannot compare instance of " +
					o.getClass().getName() + " to VersionedName");
		}
		VersionedName rhs = (VersionedName) o;
		int nameComparison = this.getName().compareTo(rhs.getName());
		if (nameComparison < 0) { 
			return -1;
		}
		else if (nameComparison > 0) {
			return 1;
		}
		// hm, same name. what about version?
		if (this.getVersion() < rhs.getVersion()) {
			return -1;
		}
		else if (this.getVersion() > rhs.getVersion()) { 
			return 1;
		}
		else {
			return 0; // if we get here, equals is broken!!	
		}
	}
	
	private int calculateHashCode() {
		HashCodeBuilder hashCodeBuilder = new HashCodeBuilder(83, 127);
		return hashCodeBuilder.append(name).append(version).toHashCode();
	}

	private String buildToStringText() {
		ToStringBuilder toStringBuilder = new ToStringBuilder(this, VersionedName.TO_STRING_STYLE);
		return toStringBuilder.append("Name:", name).append("Version:", version).toString();
	}

	/*
	 * SERIALIZATION CODE
	 */
	private void writeObject(ObjectOutputStream stream) throws IOException {
		stream.defaultWriteObject();
		stream.writeObject(name);
		stream.writeInt(version);
	}
	
	private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
		stream.defaultReadObject();
		String name = (String) stream.readObject();
		int version = stream.readInt();
		
		try {
			init(name, version);
		}
		catch (IllegalArgumentException iae) {
			// this is the appropriate exception if the stream
			// contains duff values
			throw new InvalidObjectException(iae.getMessage());
		}
	}
	
}