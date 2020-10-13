/*
 * hetao - High Performance Web Server
 * author	: calvin
 * email	: calvinwilliams@163.com
 *
 * Licensed under the LGPL v2.1, see the file LICENSE in base directory.
 */

#include "hetao_in.h"

int OnReceivingForward( struct HetaoServer *p_server , struct HttpSession *p_http_session )
{
	struct HttpBuffer	*b = NULL ;
	char			*response_base = NULL ;
	int			response_len ;
	
	struct epoll_event	event ;
	
	int			nret = 0 ;
	
	/* ��һ��HTTP��Ӧ */
	nret = ReceiveHttpResponseNonblock( p_http_session->forward_sock , NULL , p_http_session->forward_http ) ;
	if( nret == FASTERHTTP_INFO_NEED_MORE_HTTP_BUFFER )
	{
		/* û������ */
		DebugLog( __FILE__ , __LINE__ , "ReceiveHttpResponseNonblock return FASTERHTTP_INFO_NEED_MORE_HTTP_BUFFER" );
	}
	else if( nret )
	{
		/* ���ձ����� */
		if( nret == FASTERHTTP_ERROR_TCP_CLOSE )
		{
			ErrorLog( __FILE__ , __LINE__ , "http socket closed detected" );
			return 1;
		}
		else if( nret == FASTERHTTP_INFO_TCP_CLOSE )
		{
			InfoLog( __FILE__ , __LINE__ , "http socket closed detected" );
			return 1;
		}
		else
		{
			ErrorLog( __FILE__ , __LINE__ , "ReceiveHttpResponseNonblock failed[%d] , errno[%d]" , nret , errno );
			
			nret = FormatHttpResponseStartLine( abs(nret)/100 , p_http_session->http , 1 ) ;
			if( nret )
			{
				ErrorLog( __FILE__ , __LINE__ , "FormatHttpResponseStartLine failed[%d] , errno[%d]" , nret , errno );
				return 1;
			}
			
			return 0;
		}
	}
	else
	{
		/* ����һ��HTTP��Ӧ */
		DebugLog( __FILE__ , __LINE__ , "ReceiveHttpResponseNonblock done" );
		
		/* ����HTTP��Ӧ */
		response_base = GetHttpBufferBase( GetHttpResponseBuffer(p_http_session->forward_http) , & response_len ) ;
		b = GetHttpResponseBuffer(p_http_session->http) ;
		nret = MemcatHttpBuffer( b , response_base , response_len ) ;
		if( nret )
		{
			ErrorLog( __FILE__ , __LINE__ , "MemcatHttpBuffer failed , errno[%d]" , errno );
			return 1;
		}
		
		DebugHexLog( __FILE__ , __LINE__ , GetHttpBufferBase(b,NULL) , GetHttpBufferLength(b) , "HttpResponseBuffer" );
		
		SetHttpSessionUnused_02( p_server , p_http_session );
		
		/* �ָ�ԭ�����¼� */
		memset( & event , 0x00 , sizeof(struct epoll_event) );
		event.events = EPOLLOUT | EPOLLERR ;
		event.data.ptr = p_http_session ;
		nret = epoll_ctl( p_server->p_this_process_info->epoll_fd , EPOLL_CTL_MOD , p_http_session->netaddr.sock , & event ) ;
		if( nret == -1 )
		{
			ErrorLog( __FILE__ , __LINE__ , "epoll_ctl failed , errno[%d]" , errno );
			return -1;
		}
	}
	
	return 0;
}
