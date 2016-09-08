package models;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
@Table(name="ROLE")
public class Role implements Serializable {

	private static final long serialVersionUID = 3289972237554527160L;

	@Id  
    @GeneratedValue  
    @Column(name="ID")
    private Long id;
	
	@Column(name="AUTHORITY")
	private String authority;
	
	@ManyToMany(mappedBy="authorities")
	private Set<User> users = new LinkedHashSet<User>(0);

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}
	
	@Override
    public boolean equals(final Object obj) {
    	if (this == obj) {
			return true;
		}
		if (!(obj instanceof Role)) {
			return false;
		}
		Role castOther = (Role) obj;
		return new EqualsBuilder()
			.append(id, castOther.id)
			.append(authority, castOther.authority)
			.isEquals();
    }
    
    @Override
    public int hashCode() {
    	return new HashCodeBuilder()
    		.append(id)
    		.append(authority)
    		.toHashCode();
    }
    
    @Override
    public String toString() {
    	return new ToStringBuilder(this)
    	.append(id)
		.append(authority)
		.toString();
    }
}
