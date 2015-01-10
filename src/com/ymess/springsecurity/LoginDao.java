package com.ymess.springsecurity;

import org.springframework.data.cassandra.core.CassandraTemplate;

import com.ymess.pojos.UserPOJO;

public class LoginDao implements ILoginDao {

	private CassandraTemplate cassandraTemplate;
	
	@Override
	public UserPOJO authenticateUser(String username) {
		UserPOJO user = cassandraTemplate.selectOneById(UserPOJO.class, username);
		
		if(user == null)
		{
			user = new UserPOJO();
			user.setAuthority("Anonymous User");
		}
		
		return user;
	}

	public CassandraTemplate getCassandraTemplate() {
		return cassandraTemplate;
	}

	public void setCassandraTemplate(CassandraTemplate cassandraTemplate) {
		this.cassandraTemplate = cassandraTemplate;
	}

}
