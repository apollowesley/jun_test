#include <reg52.h>
#include <intrins.h>
#include "74HC595.h"


sbit sck = P2^1;
sbit rck = P2^2;
sbit si  = P2^3;


void delay()
{
	_nop_();
	_nop_();
	_nop_();
	_nop_();
}

void Writ8bit(unsigned char a)
{
	unsigned char b,i;
	sck = 0;
	rck = 0;
	delay();
	for (i=0;i<8;i++)
	{
		b = a & 0x80;
		si  = b;
		sck = 1;
		delay();
		a <<= 1;
		sck = 0;
		delay();	
	}
}
void display(unsigned char m,unsigned char n)
{
	Writ8bit(m);
	Writ8bit(n);
	Lock();
}
void Lock()
{
	delay();
	rck = 1;
	delay();
	delay();
	rck = 0;
	delay();
}
//void main()
//{
//	//unsigned char a=0x80;
//	int a[]={0x80,0x40,0x20,0x10,0x08,0x04,0x02,0x01};
//	char i;
//	while(1)
//	{
//		for(i=0;i<8;i++)
//		{
//			display(a[i]);
//			Delay500ms();
//		}
//	}
//}


//void Delay500ms()		//@12.000MHz
//{
//	unsigned char i, j, k;

//	_nop_();
//	i = 4;
//	j = 205;
//	k = 187;
//	do
//	{
//		do
//		{
//			while (--k);
//		} while (--j);
//	} while (--i);
//}



/*******************************************************************************
* 函数名         : Delay10ms
* 函数功能                   : 延时函数，延时10ms
* 输入           : c
* 输出                  : 无
*******************************************************************************/
void Delay10ms(unsigned int c)		//误差 0us
{
	unsigned char a,b;
	for(;c>0;c--)
	for(b=38;b>0;b--)
	for(a=130;a>0;a--);
}

