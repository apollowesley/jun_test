/**
 * Copyright 2013 ABSir's Studio
 * 
 * All right reserved
 *
 * Create on 2013-5-15 上午11:18:46
 */
package com.absir.core.kernel;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * @author absir
 * 
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class KernelLang {

	/** NULL_OBJECT */
	public static final Object NULL_OBJECT = new Object();

	/** NULL_OBJECTS */
	public static final Object[] NULL_OBJECTS = new Object[] {};

	/** NULL_STRING */
	public static final String NULL_STRING = "";

	/** NULL_Strings */
	public static final String[] NULL_STRINGS = new String[] {};

	/**
	 * @param one
	 * @param two
	 * @param three
	 * @return
	 */
	public static int min(int one, int two, int three) {
		return (one = one < two ? one : two) < three ? one : three;
	}

	/**
	 * @author absir
	 * 
	 */
	@SuppressWarnings("serial")
	public static class BreakException extends Exception {

	}

	@SuppressWarnings("serial")
	public static class CauseRuntimeException extends RuntimeException {

		/**
		 * @param cause
		 */
		public CauseRuntimeException(Throwable cause) {
			super(cause);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Throwable#getCause()
		 */
		@Override
		public Throwable getCause() {
			Throwable cause = super.getCause();
			while (cause != null && cause != this) {
				if (cause instanceof CauseRuntimeException) {
					cause = cause.getCause();
				}
			}

			return cause;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Throwable#printStackTrace()
		 */
		@Override
		public void printStackTrace() {
			Throwable cause = getCause();
			if (cause != null) {
				cause.printStackTrace();
			}

			super.printStackTrace();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Throwable#getMessage()
		 */
		@Override
		public String getMessage() {
			Throwable cause = getCause();
			return cause == null ? super.getMessage() : cause.getMessage();
		}
	}

	/**
	 * @author absir
	 * 
	 * @param <T>
	 */
	public static class ObjectTemplate<T> {

		/** object */
		public T object;

		/**
		 * 
		 */
		public ObjectTemplate() {

		}

		/**
		 * @param object
		 */
		public ObjectTemplate(T object) {
			this.object = object;
		}
	}

	/**
	 * @author absir
	 * 
	 * @param <K>
	 * @param <V>
	 */
	public static class ObjectEntry<K, V> implements Entry<K, V> {

		/** key */
		K key;

		/** value */
		V value;

		/**
		 * 
		 */
		public ObjectEntry() {
		}

		/**
		 * @param key
		 * @param value
		 */
		public ObjectEntry(K key, V value) {
			this.key = key;
			this.value = value;
		}

		/**
		 * @param key
		 *            the key to set
		 */
		public void setKey(K key) {
			this.key = key;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.util.Map.Entry#getKey()
		 */
		@Override
		public K getKey() {
			// TODO Auto-generated method stub
			return key;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.util.Map.Entry#getValue()
		 */
		@Override
		public V getValue() {
			// TODO Auto-generated method stub
			return value;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.util.Map.Entry#setValue(java.lang.Object)
		 */
		@Override
		public V setValue(V value) {
			// TODO Auto-generated method stub
			V val = this.value;
			this.value = value;
			return val;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#hashCode()
		 */
		@Override
		public int hashCode() {
			return KernelObject.hashCode(key);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		@Override
		public boolean equals(Object obj) {
			if (obj == null || !(obj instanceof ObjectEntry)) {
				return false;
			}

			return KernelObject.equals(key, ((ObjectEntry) obj).key);
		}
	}

	/**
	 * @author absir
	 * 
	 * @param <T>
	 */
	public static interface CloneTemplate<T> extends Cloneable {

		/**
		 * @return
		 */
		public T clone();

	}

	/**
	 * @author absir
	 * 
	 * @param <T>
	 */
	public static interface CallbackTemplate<T> {
		void doWith(T template);
	}

	/**
	 * @author absir
	 * 
	 * @param <T>
	 */
	public static interface CallbackBreak<T> {
		void doWith(T template) throws BreakException;
	}

	/**
	 * @author absir
	 * 
	 * @param <T>
	 */
	public static interface FilterTemplate<T> {
		boolean doWith(T template) throws BreakException;
	}

	/**
	 * @author absir
	 * 
	 */
	public static enum MatcherType {

		/** NONE */
		NONE {

			@Override
			public boolean matchString(String match, String string) {
				// TODO Auto-generated method stub
				return false;
			}
		},

		/** LEFT */
		LEFT {

			@Override
			public boolean matchString(String match, String string) {
				// TODO Auto-generated method stub
				return string.startsWith(match);
			}
		},

		/** RIGHT */
		RIGHT {

			@Override
			public boolean matchString(String match, String string) {
				// TODO Auto-generated method stub
				return string.endsWith(match);
			}
		},

		/** ALL */
		ALL {

			@Override
			public boolean matchString(String match, String string) {
				// TODO Auto-generated method stub
				return string.contains(match);
			}
		};

		public abstract boolean matchString(String match, String string);
	}

	/**
	 * 
	 * @author absir
	 * 
	 *         not safe in thread
	 */
	public static class PropertyFilter {

		/** includes */
		private Map<String, Entry<String, MatcherType>> includes;

		/** excludes */
		private Map<String, Entry<String, MatcherType>> excludes;

		/** propertyPath */
		private String propertyPath = "";

		/**
		 * @return
		 */
		public PropertyFilter newly() {
			PropertyFilter filter = new PropertyFilter();
			filter.includes = includes;
			filter.excludes = excludes;

			return filter;
		}

		/**
		 * 
		 */
		public void begin() {
			propertyPath = "";
		}

		/**
		 * @param property
		 * @return
		 */
		public PropertyFilter inlcude(String property) {
			if (includes == null) {
				includes = new HashMap<String, Entry<String, MatcherType>>();

			} else if (includes.containsKey(property)) {
				return this;
			}

			includes.put(property, null);
			return this;
		}

		/**
		 * @param property
		 * @return
		 */
		public void removeInclude(String property) {
			if (includes != null) {
				includes.remove(property);
			}
		}

		/**
		 * @param property
		 * @return
		 */
		public PropertyFilter exlcude(String property) {
			if (excludes == null) {
				excludes = new HashMap<String, Entry<String, MatcherType>>();

			} else if (excludes.containsKey(property)) {
				return this;
			}

			excludes.put(property, null);
			return this;
		}

		/**
		 * @param property
		 */
		public void removeExclude(String property) {
			if (excludes != null) {
				excludes.remove(property);
			}
		}

		/**
		 * @return
		 */
		public boolean isNonePath() {
			return includes == null && excludes == null;
		}

		/**
		 * @param matcher
		 * @return
		 */
		private boolean match(Map<String, Entry<String, MatcherType>> matchers) {
			for (Entry<String, Entry<String, MatcherType>> matcher : matchers.entrySet()) {
				if (matcher.getKey() == null) {
					return true;
				}

				Entry<String, MatcherType> entry = matcher.getValue();
				if (entry == null) {
					String match = matcher.getKey();
					ObjectEntry<String, MatcherType> objectEntry = new ObjectEntry<String, MatcherType>();
					int last = match.length() - 1;
					if (last >= 0) {
						if (match.charAt(0) == '*') {
							if (last > 0) {
								if (match.charAt(last) == '*') {
									if (last > 1) {
										objectEntry.setValue(MatcherType.ALL);
										objectEntry.setKey(match.substring(1, last));
									}

								} else {
									objectEntry.setValue(MatcherType.LEFT);
									objectEntry.setKey(match.substring(1, last + 1));
								}
							}

						} else if (match.charAt(last) == '*') {
							if (last > 0) {
								objectEntry.setValue(MatcherType.RIGHT);
								objectEntry.setKey(match.substring(0, last));
							}
						}
					}

					matcher.setValue(entry);
					entry = objectEntry;
				}

				if (entry.getValue() == null) {
					return true;
				}

				if (entry.getValue().matchString(entry.getKey(), propertyPath)) {
					return true;
				}
			}

			return false;
		}

		/**
		 * @return
		 */
		public boolean isMatch() {
			if (excludes != null) {
				if (match(excludes)) {
					return false;
				}
			}

			if (includes != null) {
				return match(includes);
			}

			return true;
		}

		/**
		 * @param propertyName
		 * @return
		 */
		public boolean isMatch(String propertyName) {
			if (!KernelString.isEmpty(propertyName)) {
				if (KernelString.isEmpty(propertyPath)) {
					propertyPath = propertyName;

				} else {
					propertyPath = propertyPath + "." + propertyName;
				}
			}

			return isMatch();
		}

		/**
		 * @param propertyPath
		 * @return
		 */
		public boolean isMatchPath(String propertyPath) {
			this.propertyPath = propertyPath;
			return isMatch();
		}

		/**
		 * @param propertyPath
		 * @return
		 */
		public boolean isMatchPath(String propertyPath, String propertyName) {
			setPropertyPath(propertyPath);
			return isMatch(propertyName);
		}

		/**
		 * @return the propertyPath
		 */
		public String getPropertyPath() {
			return propertyPath;
		}

		/**
		 * @param propertyPath
		 *            the propertyPath to set
		 */
		public void setPropertyPath(String propertyPath) {
			this.propertyPath = propertyPath;
		}

		/**
		 * @return the includes
		 */
		public Map<String, Entry<String, MatcherType>> getIncludes() {
			return includes;
		}

		/**
		 * @return the excludes
		 */
		public Map<String, Entry<String, MatcherType>> getExcludes() {
			return excludes;
		}
	}

	/** NULL_LIST_SET */
	public static final NullListSet NULL_LIST_SET = new NullListSet();

	/**
	 * @author absir
	 * 
	 */
	public static final class NullListSet implements List, Set {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.util.List#add(java.lang.Object)
		 */
		@Override
		public boolean add(Object e) {
			// TODO Auto-generated method stub
			return false;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.util.List#add(int, java.lang.Object)
		 */
		@Override
		public void add(int index, Object element) {
			// TODO Auto-generated method stub
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.util.List#addAll(java.util.Collection)
		 */
		@Override
		public boolean addAll(Collection c) {
			// TODO Auto-generated method stub
			return false;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.util.List#addAll(int, java.util.Collection)
		 */
		@Override
		public boolean addAll(int index, Collection c) {
			// TODO Auto-generated method stub
			return false;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.util.List#clear()
		 */
		@Override
		public void clear() {
			// TODO Auto-generated method stub
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.util.List#contains(java.lang.Object)
		 */
		@Override
		public boolean contains(Object o) {
			// TODO Auto-generated method stub
			return false;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.util.List#containsAll(java.util.Collection)
		 */
		@Override
		public boolean containsAll(Collection c) {
			// TODO Auto-generated method stub
			return false;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.util.List#get(int)
		 */
		@Override
		public Object get(int index) {
			// TODO Auto-generated method stub
			return null;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.util.List#indexOf(java.lang.Object)
		 */
		@Override
		public int indexOf(Object o) {
			// TODO Auto-generated method stub
			return -1;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.util.List#isEmpty()
		 */
		@Override
		public boolean isEmpty() {
			// TODO Auto-generated method stub
			return true;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.util.List#iterator()
		 */
		@Override
		public Iterator<Object> iterator() {
			// TODO Auto-generated method stub
			return NULL_LIST_ITERATOR;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.util.List#lastIndexOf(java.lang.Object)
		 */
		@Override
		public int lastIndexOf(Object o) {
			// TODO Auto-generated method stub
			return -1;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.util.List#listIterator()
		 */
		@Override
		public ListIterator<Object> listIterator() {
			// TODO Auto-generated method stub
			return NULL_LIST_ITERATOR;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.util.List#listIterator(int)
		 */
		@Override
		public ListIterator<Object> listIterator(int index) {
			// TODO Auto-generated method stub
			return NULL_LIST_ITERATOR;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.util.List#remove(java.lang.Object)
		 */
		@Override
		public boolean remove(Object o) {
			// TODO Auto-generated method stub
			return false;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.util.List#remove(int)
		 */
		@Override
		public Object remove(int index) {
			// TODO Auto-generated method stub
			return null;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.util.List#removeAll(java.util.Collection)
		 */
		@Override
		public boolean removeAll(Collection c) {
			// TODO Auto-generated method stub
			return false;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.util.List#retainAll(java.util.Collection)
		 */
		@Override
		public boolean retainAll(Collection c) {
			// TODO Auto-generated method stub
			return false;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.util.List#set(int, java.lang.Object)
		 */
		@Override
		public Object set(int index, Object element) {
			// TODO Auto-generated method stub
			return null;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.util.List#size()
		 */
		@Override
		public int size() {
			// TODO Auto-generated method stub
			return 0;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.util.List#subList(int, int)
		 */
		@Override
		public List<Object> subList(int fromIndex, int toIndex) {
			// TODO Auto-generated method stub
			return NULL_LIST_SET;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.util.List#toArray()
		 */
		@Override
		public Object[] toArray() {
			// TODO Auto-generated method stub
			return NULL_OBJECTS;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.util.List#toArray(T[])
		 */
		@Override
		public Object[] toArray(Object[] a) {
			// TODO Auto-generated method stub
			return NULL_OBJECTS;
		}
	}

	/** NULL_MAP */
	public static final Map<Object, Object> NULL_MAP = new Map() {

		@Override
		public void clear() {
			// TODO Auto-generated method stub
		}

		@Override
		public boolean containsKey(Object key) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean containsValue(Object value) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public Set<Entry<Object, Object>> entrySet() {
			// TODO Auto-generated method stub
			return (Set<Entry<Object, Object>>) (Set) NULL_LIST_SET;
		}

		@Override
		public Object get(Object key) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean isEmpty() {
			// TODO Auto-generated method stub
			return true;
		}

		@Override
		public Set<Object> keySet() {
			// TODO Auto-generated method stub
			return NULL_LIST_SET;
		}

		@Override
		public Object put(Object key, Object value) {
			// TODO Auto-generated method stub
			return NULL_LIST_SET;
		}

		@Override
		public void putAll(Map m) {
			// TODO Auto-generated method stub
		}

		@Override
		public Object remove(Object key) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public int size() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public Collection<Object> values() {
			// TODO Auto-generated method stub
			return NULL_LIST_SET;
		}
	};

	/** NULL_LIST_ITERATOR */
	public static final ListIterator NULL_LIST_ITERATOR = new ListIterator() {

		@Override
		public void add(Object e) {
			// TODO Auto-generated method stub
		}

		@Override
		public boolean hasNext() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean hasPrevious() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public Object next() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public int nextIndex() {
			// TODO Auto-generated method stub
			return -1;
		}

		@Override
		public Object previous() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public int previousIndex() {
			// TODO Auto-generated method stub
			return -1;
		}

		@Override
		public void remove() {
			// TODO Auto-generated method stub
		}

		@Override
		public void set(Object e) {
			// TODO Auto-generated method stub
		}
	};
}
