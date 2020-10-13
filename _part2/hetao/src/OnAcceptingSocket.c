/*
 * hetao - High Performance Web Server
 * author	: calvin
 * email	: calvinwilliams@163.com
 *
 * Licensed under the LGPL v2.1, see the file LICENSE in base directory.
 */

#include "hetao_in.h"

int OnAcceptingSocket( struct HetaoServer *p_server , struct ListenSession *p_listen_session )
{
	SOCKET			sock ;
	struct sockaddr_in	addr ;
	SOCKLEN_T		accept_addr_len ;
	SSL			*ssl = NULL ;
	X509			*x509 = NULL ;
	char			*line = NULL ;
	
	struct HttpSession	*p_http_session = NULL ;
	
	struct epoll_event	event ;
	
	int			nret = 0 ;
	
	/* ѭ���������������ӣ�ֱ��û���� */
	while(1)
	{
		/* ���������� */
		accept_addr_len = sizeof(struct sockaddr) ;
		sock = accept( p_listen_session->netaddr.sock , (struct sockaddr *) & addr , & accept_addr_len );
		if( sock == -1 )
		{
			if( errno == EAGAIN || errno == EWOULDBLOCK )
				break;
			
			ErrorLog( __FILE__ , __LINE__ , "accept failed , errno[%d]" , errno );
			return -1;
		}
		else
		{
			InfoLog( __FILE__ , __LINE__ , "accept ok , listen #%d# accept #%d#" , p_listen_session->netaddr.sock , sock );
		}
		
		/* SSL���� */
		if( p_server->ssl_ctx )
		{
			ssl = SSL_new( p_server->ssl_ctx ) ;
			if( ssl == NULL )
			{
				ErrorLog( __FILE__ , __LINE__ , "SSL_new failed , errno[%d]" , errno );
				close( sock );
				return 1;
			}
			
			SSL_set_fd( ssl , sock );
			
			nret = SSL_accept( ssl ) ;
			if( nret == -1 )
			{
				ErrorLog( __FILE__ , __LINE__ , "SSL_accept failed , errno[%d]" , errno );
				SSL_free( ssl ) ;
				close( sock );
				return 1;
			}
			
			x509 = SSL_get_peer_certificate( ssl ) ;
			if( x509 )
			{
				line = X509_NAME_oneline( X509_get_subject_name(x509) , 0 , 0 );
				free( line );
				line = X509_NAME_oneline( X509_get_issuer_name(x509) , 0 , 0 );
				free( line );
				X509_free( x509 );
			}
		}
		
		/* ���һ���µĻỰ�ṹ */
		p_http_session = FetchHttpSessionUnused( p_server ) ;
		if( p_http_session == NULL )
		{
			ErrorLog( __FILE__ , __LINE__ , "FetchHttpSessionUnused failed , errno[%d]" , errno );
			close( sock );
			return 1;
		}
		p_http_session->type = DATASESSION_TYPE_HTTP ;
		
		p_http_session->netaddr.sock = sock ;
		memcpy( & (p_http_session->netaddr.addr) , & addr , sizeof(struct sockaddr_in) ) ;
		
		p_http_session->netaddr.ip[sizeof(p_http_session->netaddr.ip)-1] = '\0' ;
		inet_ntop( AF_INET , & (p_http_session->netaddr.addr) , p_http_session->netaddr.ip , sizeof(p_http_session->netaddr.ip) );
		
		if( p_server->ssl_ctx )
		{
			p_http_session->ssl = ssl ;
		}
		
		/* ����TCPѡ�� */
		SetHttpNonblock( p_http_session->netaddr.sock );
		SetHttpNodelay( p_http_session->netaddr.sock , p_server->p_config->tcp_options.nodelay );
		SetHttpNoLinger( p_http_session->netaddr.sock , p_server->p_config->tcp_options.nolinger );
		
		/* ע��epoll���¼� */
		memset( & event , 0x00 , sizeof(struct epoll_event) );
		event.events = EPOLLIN | EPOLLERR ;
		event.data.ptr = p_http_session ;
		nret = epoll_ctl( p_server->p_this_process_info->epoll_fd , EPOLL_CTL_ADD , p_http_session->netaddr.sock , & event ) ;
		if( nret == -1 )
		{
			ErrorLog( __FILE__ , __LINE__ , "epoll_ctl #%d# add #%d# failed , errno[%d]" , p_server->p_this_process_info->epoll_fd , p_http_session->netaddr.sock , errno );
			SetHttpSessionUnused( p_server , p_http_session );
			return -1;
		}
		else
		{
			DebugLog( __FILE__ , __LINE__ , "epoll_ctl #%d# add #%d#" , p_server->p_this_process_info->epoll_fd , p_http_session->netaddr.sock );
		}
		
		/* ������һ�� */
		/*
		if( p_server->p_config->worker_processes == 1 )
		{
			nret = OnReceivingSocket( p_server , p_http_session ) ;
			if( nret > 0 )
			{
				DebugLog( __FILE__ , __LINE__ , "OnReceivingSocket done[%d]" , nret );
				SetHttpSessionUnused( p_server , p_http_session );
			}
			else if( nret < 0 )
			{
				ErrorLog( __FILE__ , __LINE__ , "OnReceivingSocket failed[%d] , errno[%d]" , nret , errno );
				return -1;
			}
			else
			{
				DebugLog( __FILE__ , __LINE__ , "OnReceivingSocket ok" );
			}
		}
		*/
	}
	
	return 0;
}

