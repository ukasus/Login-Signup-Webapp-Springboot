package com.ujjawal.ListingUsersWithSecurity.Repo;

import org.springframework.data.mongodb.repository.MongoRepository;


import com.ujjawal.ListingUsersWithSecurity.Entity.UserInfo;

public interface UserRepository extends MongoRepository<UserInfo, String> {

	UserInfo findByEmail(String email);
}
