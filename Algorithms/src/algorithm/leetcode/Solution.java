package algorithm.leetcode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;


public class Solution {
	public static void main(String[] args) {
		int[] input = new int[] {4,3,2,7,8,2,3,1};
		int[] input2 = new int[] {11,7,15,2};
		int[] input3 = new int[] {-1, 0, 1, 2, -1, -4};
		int[] input4 = new int[] {-1, 2, 1, -4};
		int[] input5 = new int[] {1, 0, -1, 0, -2, 2};
		String test = "abcabcbbb";
		int[] input6 = new int[] {1,2};
		int[] input7 = new int[] {3,4};
		int[] stones = new int[] {0,1,3,5,6,8,12,17};
		int[] stones2 = new int[] {0,1,2,3,4,8,9,11};

		/*
		 * Input: (7 -> 2 -> 4 -> 3) + (5 -> 6 -> 4)
		 */
		ListNode<Integer> l1 = arrayToLinkedList(new int[] {7,2,4,3});
		System.out.println("input list: "+l1);
		ListNode<Integer> l2 = arrayToLinkedList(new int[] {5,6,4});
		System.out.println("input list 2: "+ l2);
		Solution sol = new Solution();
		System.out.println("binarySearchTest1: "+sol.binarySearch(stones, 0, 7, 3));
		System.out.println("solution is: "+sol.findDuplicates(input));
		System.out.println("solution2 is: "+sol.findDuplicates2(input));
		int[] ans2=sol.twoSum(input2, 9);
		System.out.println("solution 1 is: ["+ans2[0]+","+ans2[1]+"]");
		List<List<Integer>> ans3 = sol.threeSum(input3);
		System.out.println("threeSum ans: "+ans3);
		System.out.println("3sumClosest ans: "+sol.ThreeSumClosest(input4, 1));
		//System.out.println("4sum ans: "+sol.FourSum(input5, 0));
		System.out.println("addTwoBigNumbers ans:"+sol.addTwoNumbers(l1, l2));
		System.out.println("ans to longestSubString: "+sol.longestSubString(test));
		System.out.println("median number is: "+sol.findMedianSortedArrays(input6, input7));
		System.out.println("can cross stones: "+stones+" ? "+sol.canCross(stones));
		System.out.println("can cross stones2: "+stones2+" ? "+sol.canCross(stones2));
	}

	private static ListNode<Integer> arrayToLinkedList(int[] array) {
		if (array == null || array.length < 1) return null;
		
		//convert array to linkedlist
		ListNode<Integer> res = new ListNode<Integer>(array[0]);
		ListNode<Integer> temp = res;
		for (int i = 1; i < array.length ; i++) {
			temp.next = new ListNode<Integer>(array[i]);
			temp = temp.next;
		}
		return res;
	}

	/*
		442. Find All Duplicates in an Array 

		Given an array of integers, 1 ≤ a[i] ≤ n (n = size of array), 

		some elements appear twice and others appear once.

		Find all the elements that appear twice in this array.

		Could you do it without extra space and in O(n) runtime?

	 	Input:
			[4,3,2,7,8,2,3,1]

	 	Output:
			[2,3]
	 */
	public List<Integer> findDuplicates (int[] nums) {
		//use extra space to store the hashmap of the array integers
		//like this because it applies to general case.
		List<Integer> ans = new LinkedList<Integer>();
		Map<Integer,Integer> mp = new HashMap<Integer,Integer>();
		for (int i : nums) {
			if (mp.containsKey(i)) {
				ans.add(i);
				mp.put(i, mp.get(i)+1);
			} else {
				mp.put(i, 1);
			}
		}
		return ans;
	}

	public List<Integer> findDuplicates2 (int[] nums) {
		// use clever hashing without extra space to store the hashmap of the array integers
		// use the integer to hash to the index of the array, and flip the sign of the int
		// in that array index location to store duplication info
		List<Integer> ans = new LinkedList<Integer>();
		//Map<Integer,Integer> mp = new HashMap<Integer,Integer>();
		for (int i : nums) {
			//pay attention to index range: 0:n-1 vs. value range: 1:n
			// the mapping equation is x-1:
			int temp = Math.abs(i); //holds the index value + 1
			if (nums[temp-1] < 0) {
				ans.add(temp);
			} else {
				nums[temp-1] = -nums[temp-1];
			}
		}
		return ans;
	}

	/*
	 * 1. Given an array of integers, return indices of the two numbers such that 
	   they add up to a specific target.

		You may assume that each input would have exactly one solution.

	 	Given nums = [2, 7, 11, 15], target = 9,

		Because nums[0] + nums[1] = 2 + 7 = 9,
		return [0, 1]
	 */
	public int[] twoSum(int[] nums, int target) {
		int[] ans = null;
		int index1 = 0 ;
		int index2 = nums.length-1;
		//that is choose n,2 number of possibilities
		// make a copy of the original int array before sorting
		int[] numsorted = nums.clone();
		Arrays.sort(numsorted);
		while (index1 < index2 ) {
			if(numsorted[index2] == target - numsorted[index1]) {
				//find the one and only solution
				ans = new int[2];
				ans[0] = getOriginIndex(nums,numsorted[index1]);
				ans[1] = getOriginIndex(nums,numsorted[index2]);
				break;
			} else if (numsorted[index1] < target - numsorted[index2]) {
				//if twoSum(left,right) < target; then increase the left is the only way
				index1++;
			} else {
				//if twoSum(left,right) > target; then decrease the right is the only way
				index2--;
			}
		}

		/*
		for (int i = 0; i < numsorted.length; i++) {
			if (numsorted[i] > target) break;
			for (int j = i+1; j < numsorted.length; j++) {
				if(numsorted[j] > target - numsorted[i]) break;
				if(numsorted[j] == target -numsorted[i]) {
					//find the one and only solution
					ans[0] = getOriginIndex(nums,numsorted[i]);
					ans[1] = getOriginIndex(nums,numsorted[j]);
					break;
				}
			}
		}
		 */
		return ans;
	}

	public int[] twoSumSorted(int[] nums, int target) {
		//assert nums are already sorted by calling functions.
		int[] ans = null;
		int index1 = 0 ;
		int index2 = nums.length-1;
		//that is choose n,2 number of possibilities
		// make a copy of the original int array before sorting
		int[] numsorted = nums.clone();
		//Arrays.sort(numsorted);
		while (index1 < index2 ) {
			if(numsorted[index2] == target - numsorted[index1]) {
				//find the one and only solution
				ans = new int[2];
				ans[0] = index1;//getOriginIndex(nums,numsorted[index1]);
				ans[1] = index2;//getOriginIndex(nums,numsorted[index2]);
				break;
			} else if (numsorted[index1] < target - numsorted[index2]) {
				//if twoSum(left,right) < target; then increase the left is the only way
				index1++;
			} else {
				//if twoSum(left,right) > target; then decrease the right is the only way
				index2--;
			}
		}
		return ans;
	}

	/*
	 * Given an array S of n integers, are there elements a, b, c in S 
	 * such that a + b + c = 0? 
	 * Find all unique triplets in the array which gives the sum of zero.
	 * For example, given array S = [-1, 0, 1, 2, -1, -4],
	 * Note: The solution set must not contain duplicate triplets.
		A solution set is:
		[
		  [-1, 0, 1],
		  [-1, -1, 2]
		]
	 */
	public List<List<Integer>> threeSum(int[] nums) {
		List<List<Integer>> ans = new LinkedList<>();
		int[] numsorted = nums.clone();
		Arrays.sort(numsorted);
		
		//solutionA: convert it to twoSum problem?
		//attention: can not assume one unique solution for twoSum, need find all pairs. (ie., can
		// no longer break after first pair found.
		
		/*int target;
		for (int i = 0; i < numsorted.length; i++) {
			//here we provide subarray from i+1:n-1

			//to avoid duplication...:
			target = -numsorted[i];
			int[] subarray = Arrays.copyOfRange(numsorted, i+1, numsorted.length);
			int[] index = twoSumSorted(subarray,target);
			if ( null != index ) {
				//we find a solution!
				ans.add(Arrays.asList(numsorted[i],subarray[index[0]],subarray[index[1]]));
			}
		}*/
		

		//solutionB: put everything in one function call
		int i,j,k; //three indices 
		int len = numsorted.length;
		for (i = 0; i < len-2; i++) {
			j = i+1;
			k = len-1;
			//skip duplicates
			if (i > 0 && numsorted[i] == numsorted[i-1]) continue;
			while (j < k) {
				if ( numsorted[i] + numsorted[j] + numsorted[k] == 0) {
					//find one triplet:
					ans.add(Arrays.asList(numsorted[i],numsorted[j],numsorted[k]));
					//avoid duplicate
					while ((j < k) && numsorted[j+1] == numsorted[j]) j++;
					while ((j < k) && numsorted[k-1] == numsorted[k]) k--;
					//continue to find all triplets for given i:
					j++; k--;
				} else if (numsorted[j] + numsorted[k] < -numsorted[i]) {
					j++;
				} else {
					k--;
				}
			}
		}
		return ans;
	}
	
	/*
	 * 3Sum closest
	 * Given an array S of n integers, find three integers in S such that the sum is closest to a given number, target. Return the sum of the three integers. 
	 * You may assume that each input would have exactly one solution.

    For example, given array S = {-1 2 1 -4}, and target = 1.

    The sum that is closest to the target is 2. (-1 + 2 + 1 = 2).
	 */
	
	public int ThreeSumClosest(int[] nums, int target) {
		int len = nums.length;
		if ( len < 3 ) throw new IllegalArgumentException("nums[] must contain at least 3 numers");
		int[] numsorted = nums.clone();
		int i,j,k,targetsum,sum;
		Arrays.sort(numsorted);
		targetsum = numsorted[0]+numsorted[1]+numsorted[len-1];
		if (targetsum == target) return target; //assume one and only solution
		//we want to minimize targetsum - target
		for (i = 0; i < len - 2; i++) {
			if ( i == 0 || (i > 0 && numsorted[i] != numsorted[i-1])) {
				j = i + 1;
				k = numsorted.length - 1;
				while (j < k) {
					sum = numsorted[i]+numsorted[j]+numsorted[k];
					if (sum == target) return target;
					targetsum = Math.abs(sum - target) < Math.abs(targetsum - target) ? sum : targetsum;
					while ( j < k && numsorted[j] == numsorted[j+1]) j++;
					while ( j < k && numsorted[k] == numsorted[k-1]) k--;
					if ( sum < target) {
						j++;
					} else { //sum > target
						k--;
					}
				}
			}
		}
		
		return targetsum;
	}
	
	/* 4Sum:
	 * Given an array S of n integers, are there elements a, b, c, and d in S such that a + b + c + d = target? Find all unique quadruplets in the array which gives the sum of target.

	Note: The solution set must not contain duplicate quadruplets.

	For example, given array S = [1, 0, -1, 0, -2, 2], and target = 0.

	A solution set is:
	[
	  [-1,  0, 0, 1],
	  [-2, -1, 1, 2],
	  [-2,  0, 0, 2]
	]
	 */
	public List<List<Integer>> FourSum(int[] nums, int target) {
		int len = nums.length;
		int lo,hi;
		if (len < 4 || nums == null) throw new IllegalArgumentException("input must be a least 4 numbers");
		List<List<Integer>> ans = new LinkedList<>();
		//int[] numsorted = nums.clone();
		Arrays.sort(nums);
		//prune some branches:
		if (4*nums[0] > target || 4*nums[len-1] < target) return ans;
		for (int i = 0; i < len; i++) {
			//for each num[i], there are three sum problem to solve:
			lo = i + 1;
			hi = len - 1;
			//skip duplicates
			if (i > 0 && nums[i] == nums[i-1]) continue;
			//further prune some branches
			if ( nums[i] + 3*nums[len-1] < target) continue;
			if ( 4*nums[i] > target) break;
			if (4*nums[i] == target) {
				if ( i+3 < len && nums[i] == nums[i+3]) {
					//edge case:
					ans.add(Arrays.asList(nums[i],nums[i+1],nums[i+2],nums[i+3]));
					break;
				}
			}
			ThreeSumSub(nums, target - nums[i],lo, hi, nums[i], ans);
		}
		return ans;
	}

	private void ThreeSumSub(int[] nums, int target, int lo, int hi, int Fourth, List<List<Integer>> ans) {
		// nums  are already sorted
		if (lo >= hi) return;
		//prune the branches
		if (3*nums[lo] > target || 3*nums[hi] < target) return;
		int i = lo;
		for(; i < hi - 1; i++) {
			lo = i + 1;
			if (i > lo && nums[i] == nums[i-1]) continue;
			TwoSumSub(nums, target - nums[i], lo, hi, nums[i], Fourth, ans);
		}
		
	}

	private void TwoSumSub(int[] nums, int target, int lo, int hi, int Third, int Fourth, List<List<Integer>> ans) {
		//nums are already sorted
		if ( lo >= hi) return;
		//prune some branches:
		if ( 2*nums[hi] < target || 2*nums[lo] > target) return;
		
		while ( lo < hi ) {
			if (nums[lo] + nums[hi] == target) {
				//found the tuple
				ans.add(Arrays.asList(Fourth,Third,nums[lo],nums[hi]));
				//we need remove all duplicates
				while(lo < hi && nums[lo] == nums[lo+1]) lo++;
				while(lo < hi && nums[hi] == nums[hi-1]) hi--;
			} else if (nums[lo] + nums[hi] < target) {
				lo++;
			} else {
				hi--;
			}
		}
		
	}
	
	/*
	 * You are given two linked lists representing two non-negative numbers. The most significant digit 
	 * comes first and each of their nodes contain a single digit. 
	 * Add the two numbers and return it as a linked list.

		You may assume the two numbers do not contain any leading zero, except the number 0 itself.
		
		Follow up:
		What if you cannot modify the input lists? In other words, reversing the lists is not allowed.
		
		Example:
		
		Input: (7 -> 2 -> 4 -> 3) + (5 -> 6 -> 4)
		Output: 7 -> 8 -> 0 -> 7
	 */
	
	 public ListNode<Integer> addTwoNumbers(ListNode<Integer> l1, ListNode<Integer> l2) {
		 ListNode<Integer> res;//= new ListNode<Integer> ();
		 //what if you cannot modify the input lists? reversing the lists is not allowed
		 Stack<Integer> s1 = new Stack();
		 Stack<Integer> s2 = new Stack();
		 Stack<Integer> s = new Stack();
		 ListNode<Integer> p1 = l1;
		 ListNode<Integer> p2 = l2;
		 ListNode<Integer> p;
		 int sum;
		 int carry = 0;
		 while(p1 != null) {
			 s1.push(p1.value);
			 p1 = p1.next;
		 }
		 while(p2 != null) {
			 s2.push(p2.value);
			 p2 = p2.next;
		 }
		 while (!s1.isEmpty() && !s2.isEmpty()) {
			 sum = s1.pop() + s2.pop()+carry;
			 carry = sum >=10? 1:0; 
			 s.push(sum < 10? sum:sum-sum/10*10);
		 }
		 while (!s1.isEmpty()) {
			 s.push(s1.pop());
		 }
		 while (!s2.isEmpty()) {
			 s.push(s2.pop());
		 }
		 res = new ListNode<Integer>(s.pop());
		 p = res;
		 while (!s.isEmpty()) {
			 p.next = new ListNode<Integer>(s.pop());
			 p=p.next;
		 }
		 return res;
		 
		 /*
		  * reverse a linked list
		  * add them by most significant bit first, need carry bit
		  * populate the entire list one node at a time
		  */
		 /*method 1, reverse linked list
		 ListNode rl1 = reverse(l1);
		 System.out.println("reverse list1: "+rl1);
		 ListNode rl2 = reverse(l2);
		 System.out.println("reverse list2: "+rl2);
		 ListNode<Integer> p = res; //pointer to the res listnode
		 ListNode<Integer> p1 = rl1; //pointer to the res listnode
		 ListNode<Integer> p2 = rl2; //pointer to the res listnode
		 
		 //assume l1 and l2 are already aligned by its most significant bit:
		 int sum = p1.value + p2.value;
		 res.value= sum < 10? sum: sum - sum/10*10;
		 int carry = sum > 10 ? 1:0;
		 p1=p1.next;
		 p2=p2.next;
		 while (p1 != null && p2 != null) {
			 sum = p1.value + p2.value + carry;
			 carry = sum >=10? 1:0; 
			 p.next= new ListNode<Integer>(sum < 10? sum:sum-sum/10*10);
			 p = p.next;
			 p1 = p1.next;
			 p2 = p2.next;
			 //System.out.println("res: "+res);
		 }
		 //either p1 is null or p2 is null
		 while (p1 != null) {
			 //add rest of the p1 to p
			 p.next = new ListNode<Integer>(p1.value);
			 p = p.next;
			 p1 = p1.next;
		 } 
		 
		 while (p2 != null) {
				 //add rest of the p2 to p
				 p.next = new ListNode<Integer>(p2.value);
				 p = p.next;
				 p2 = p2.next;
		}
		 System.out.println("res: "+res);
		return reverse(res);
		*/
	 }
	
	/*
	 * Given a string, find the length of the longest substring without repeating characters.

		Examples:
		
		Given "abcabcbb", the answer is "abc", which the length is 3.
		
		Given "bbbbb", the answer is "b", with the length of 1.
		
		Given "pwwkew", the answer is "wke", with the length of 3. Note that the answer must be a substring, 
		"pwke" is a subsequence and not a substring.
	 */
	 
	 public String longestSubString(String s) {
		 final int SIZE = 128; //size of ascii table
		 boolean[] table;
		 String res = s.substring(0, 1);
		 for (int i = 0; i < s.length(); i++) {
			 table = new boolean[SIZE];
			 table[s.charAt(i)]=true;
			 int j = i+1;
			 for (; j < s.length(); j++) {
				 if (table[s.charAt(j)]) break;
				 table[s.charAt(j)] = true;
			 }
			 if ( j-i > res.length()) {
				 res = s.substring(i,j);
				 System.out.println("sub: "+res);
			 }
		 }
		 return res;
	 }

	private static ListNode reverse(ListNode head) {
		// []->[]->[]
		// []<-[]<-[]
		if (head == null || head.next == null) return head;
		ListNode prev = null;
		ListNode curr = null;
		while ( head.next!= null) {
			//System.out.println(head);
			curr = head.next;
			head.next = prev;
			prev = head;
			head = curr;
		}
		//System.out.println(head);
		head.next = prev;
		return head;
	}
	
	/*
	 * There are two sorted arrays nums1 and nums2 of size m and n respectively.

		Find the median of the two sorted arrays. The overall run time complexity should be O(log (m+n)).
		
		Example 1:
		nums1 = [1, 3]
		nums2 = [2]
		
		The median is 2.0
		Example 2:
		nums1 = [1, 2]
		nums2 = [3, 4]
		
		The median is (2 + 3)/2 = 2.5
	 */
	
	public double findMedianSortedArrays(int[] nums1, int[] nums2) {
		//double res1 = findMedian(nums1);
		//double res2 = findMedian(nums2);
		int[] nums = mergeSortedArrays(nums1,nums2);
		return findMedian(nums);
	}

	private int[] mergeSortedArrays(int[] nums1, int[] nums2) {
		//nums1 and nums2 are already sorted
		int len1 = nums1.length;
		int len2 = nums2.length;
		int len = len1+len2;
		int[] res = new int[len];
		int i = 0;
		int j = 0;
		int k = 0;
		while( j < len1 && k < len2 ) {
			res[i++] = nums1[j] < nums2[k] ? nums1[j++] : nums2[k++];
		}
		while (j < len1) {
			res[i++] = nums1[j++];
		}
		while ( k < len2) {
			res[i++] = nums2[k++];
		}
		return res;
	}

	private double findMedian(int[] nums) {
		//the array is already sorted
		assert(nums.length>0);
		for(int i:nums) {
			System.out.println("nums: "+i);
		}
		int len = nums.length;
		System.out.println("len: "+len);
		if ((len & 0x01) == 0) {
			int a = len>>>1;
			int b = (len>>>1)-1;
			return (nums[a]+nums[b])/2.0;
		} else {
			int a = len>>>1;
			return nums[a];
		}
	}
	
	/*
	 * 403. Frog Jump   QuestionEditorial Solution  My Submissions

		A frog is crossing a river. The river is divided into x units and at each unit there may or may not exist a stone. The frog can jump on a stone, but it must not jump into the water.
		
		Given a list of stones' positions (in units) in sorted ascending order, determine if the frog is able to cross the river by landing on the last stone. 
		
		Initially, the frog is on the first stone and assume the first jump must be 1 unit.
		
		If the frog's last jump was k units, then its next jump must be either k - 1, k, or k + 1 units. Note that the frog can only jump in the forward direction.
		
		Note:
		
		The number of stones is ≥ 2 and is < 1,100.
		Each stone's position will be a non-negative integer < 231.
		The first stone's position is always 0.
		Example 1:
		
		[0,1,3,5,6,8,12,17]
		
		There are a total of 8 stones.
		The first stone at the 0th unit, second stone at the 1st unit,
		third stone at the 3rd unit, and so on...
		The last stone at the 17th unit.
		
		Return true. The frog can jump to the last stone by jumping 
		1 unit to the 2nd stone, then 2 units to the 3rd stone, then 
		2 units to the 4th stone, then 3 units to the 6th stone, 
		4 units to the 7th stone, and 5 units to the 8th stone.
		Example 2:
		
		[0,1,2,3,4,8,9,11]

		Return false. There is no way to jump to the last stone as 
		the gap between the 5th and 6th stone is too large.
	 */
	
	public boolean canCross(int[] stones) {
		boolean res = false;
		int len = stones.length;
		assert (len >= 2 && len <1100);
		if (len == 2 && stones[1] == 1 && stones[0] == 0) return true;
		//frog start at stone 0:
		//graph search using dfs:
		//guess: if at position i, how to get the next stone,last jump was k steps
		int i = 1;
		int k = 1; //first jump is always 1 step
		return dfs(i,k,stones);
	}
	
	private boolean dfs(int pos, int step, int[] stones) {
		System.out.println("pos: "+pos);
		final int[] steps = new int[] {-1,0,1};
		if (pos == stones.length - 1) return true; //congratulations, you have reached the last stone!!!
		//if (pos >= stones.length) return false;
		if (stones[pos+1] - stones[pos] > step + 1) return false;//next stone not reachable, max jump is step + 1
		for (int deltaStep : steps) {
			int nextPos = findNextPos(pos,step+deltaStep,stones);
			/*int next2 = findNextPos(pos,step+1,stones);
			int next3 = findNextPos(pos,step-1,stones);*/
	
		if ((nextPos != -1) && nextPos > pos && dfs(nextPos, step+deltaStep, stones)) return true;
		/*if ((next2 != -1) && next2 > pos && dfs(next2, step+1,stones)) return true;
		if ((next3 != -1) && next3 > pos && dfs(next3, step-1, stones)) return true;*/
		}
		return false; //all possibilities are reached
	}
	
	private int findNextPos(int pos, int step, int[] stones) {
		// given the current stone location and previous step takes to jump to this stone, 
		// give the next stone location 
		// with the exact step, return -1 if not possible.
		int res = -1;
		int end = stones.length-1;
		res = binarySearch(stones,pos,end,stones[pos]+step);
		return res;
	}

	public int binarySearch(int[] a, int start, int end, int val) {
		System.out.println("target: "+val);
		if ( start < 0 || end < 0 || start > a.length-1 || end > a.length-1 || end < start) return -1;
		//else if (end == start) return val == a[start] ? start:-1;
		int mid = -1;
		/*int mid = (end-start)/2+start;
		if (val < a[mid] ) return binarySearch(a, start, mid, val);
		else  if (val > a[mid]) return binarySearch(a, mid+1,end, val);
		else return mid;
		*/
		
		while (start <= end) {
			mid = (end-start)/2+start;
			//System.out.println("start: "+start+" mid: "+mid+" end: "+end+" midVal: "+a[mid]);
			if (val < a[mid]) {
				end = mid - 1;
			} else if (val > a[mid]) {
				start = mid + 1;
			} else { //val == a[mid], find it!
				return mid;
			}
		}
		return -1;
	}

	private int getOriginIndex(int[] nums, int val) {
		int ans = 0;
		for (int i = 0; i < nums.length; i++) {
			if (nums[i] == val) {
				ans = i;
				break;
			}
		}
		return ans;
	}
}
