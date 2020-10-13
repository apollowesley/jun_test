import java.util.Arrays;

/**
 * @创建人 luoxiang
 * @创建时间 2019/9/24  19:50
 * @描述 242. 有效的字母异位词        https://leetcode-cn.com/problems/valid-anagram/submissions/
 */
public class LeetCode_242 {
    public static void main(String[] args) {
        System.out.println(new LeetCode_242().isAnagram2("anagram", "nagaram"));
    }

    /**
     * Created by LuoXiang on 2019/09/24 20:00
     * Desc: 思路 —— 用 26 个长度的数组来存相应的数量，第一个用来++，第二个用来 -- ；全都为0 说明 true
     * 复杂度：       时间复杂度：O(n)  ;   空间复杂度:  O(n)
     **/
    public boolean isAnagram2(String s, String t) {
        if (s.length() != t.length()) return false;
        char[] sChars = s.toCharArray();
        char[] tChars = t.toCharArray();
        int[] alph = new int[26];
        for (int i = 0; i < sChars.length; i++) {
            alph[sChars[i] - 'a']++;
            alph[tChars[i] - 'a']--;
        }
        for (int i : alph) {
            if (i != 0) return false;
        }
        return true;
    }

    /**
     * Method 1
     * Created by LuoXiang on 2019/09/24 19:52
     * Desc: 思路 —— 字符串拆成字符数组，排序，一一比对
     * 复杂度：       时间复杂度：O(nlogn+n)  ;   空间复杂度：O(n)
     **/
    public boolean isAnagram(String s, String t) {
        char[] sChars = s.toCharArray();
        char[] tChars = t.toCharArray();
        if (sChars.length != tChars.length) return false;
        Arrays.sort(sChars);
        Arrays.sort(tChars);
        for (int i = 0; i < sChars.length; i++) {
            if (sChars[i] != tChars[i]) return false;
        }
        return true;
    }
}
