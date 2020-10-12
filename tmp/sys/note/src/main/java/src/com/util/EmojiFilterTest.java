/**
 * 
 * @auther wuwang
 * @createTime 2014-11-13 下午11:03:41
 */
package com.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;

import org.junit.Test;

/**
 * 
 * 
 * @author peaches
 */

public class EmojiFilterTest {

	/**
	 * 
	 * 
	 * 测试emoji表情
	 */

	@Test
	public void fileterEmoji() {

		String s = "<body>口口213这是一个有各种内容的消息,  Hia Hia Hia !!!! xxxx@@@...*)!" +

		"(@*$&@(&#!)@*)!&$!)@^%@(!&#. 口口口], ";

		String c = EmojiFilter.filterEmoji(s);

		assertFalse(s.equals(c));

		String expected = "<body>213这是一个有各种内容的消息,  Hia Hia Hia !!!! xxxx@@@...*)" +

		"!(@*$&@(&#!)@*)!&$!)@^%@(!&#. ], ";

		assertEquals(expected, c);

		// assertSame(c, expected);

		assertSame(expected, "<body>213这是一个有各种内容的消息,  Hia Hia Hia !!!! xxxx@@@...*)" +

		"!(@*$&@(&#!)@*)!&$!)@^%@(!&#. ], ");

		assertSame(c, EmojiFilter.filterEmoji(c));

	}

}
