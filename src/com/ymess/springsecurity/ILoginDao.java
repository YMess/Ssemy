package com.ymess.springsecurity;

import com.ymess.pojos.UserPOJO;

public interface ILoginDao {

	UserPOJO authenticateUser(String username);

}
