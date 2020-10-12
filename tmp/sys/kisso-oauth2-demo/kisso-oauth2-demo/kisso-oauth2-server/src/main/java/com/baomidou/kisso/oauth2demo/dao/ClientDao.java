package com.baomidou.kisso.oauth2demo.dao;

import java.util.List;

import com.baomidou.kisso.oauth2demo.entity.Client;

public interface ClientDao {

	public Client createClient( Client client );


	public Client updateClient( Client client );


	public void deleteClient( Long clientId );


	Client findOne( Long clientId );


	List<Client> findAll();


	Client findByClientId( String clientId );


	Client findByClientSecret( String clientSecret );

}
