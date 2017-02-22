package com.cooksys.ftd.assignments.collections;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.cooksys.ftd.assignments.collections.hierarchy.Hierarchy;
import com.cooksys.ftd.assignments.collections.model.Capitalist;
import com.cooksys.ftd.assignments.collections.model.FatCat;
import com.cooksys.ftd.assignments.collections.model.WageSlave;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class MegaCorp implements Hierarchy<Capitalist, FatCat> {

	public HashMap<Capitalist, FatCat> corpMap = new HashMap<>();

	/**
	 * Adds a given element to the hierarchy.
	 * <p>
	 * If the given element is already present in the hierarchy, do not add it
	 * and return false
	 * <p>
	 * If the given element has a parent and the parent is not part of the
	 * hierarchy, add the parent and then add the given element
	 * <p>
	 * If the given element has no parent but is a Parent itself, add it to the
	 * hierarchy
	 * <p>
	 * If the given element has no parent and is not a Parent itself, do not add
	 * it and return false
	 *
	 * @param capitalist
	 *            the element to add to the hierarchy
	 * @return true if the element was added successfully, false otherwise
	 */
	@Override
	public boolean add(Capitalist capitalist) {
		if(capitalist == null){
			return false;
		}
		//if capitalist is already present do not add it and return false
		if (corpMap.containsKey(capitalist) == false) {
			//if capitalist has parent and parent is not part of hierarchy, add parent and element.
			if (capitalist.hasParent() == true && corpMap.containsKey(capitalist.getParent()) == false) {
				return add(capitalist.getParent());
			}else if (capitalist.hasParent() == false) {//if capitalist has no parent...
				if(capitalist instanceof WageSlave){//... and is not a parent don't add and return false
					return false;
				}else if(!corpMap.containsValue(capitalist)){//... and is a parent add and return true.
					corpMap.put(capitalist, capitalist.getParent());
					return true;
				}
			} else {
				corpMap.put(capitalist, capitalist.getParent());
				return true;
			}
		}
		return false;

	}

	/**
	 * @param capitalist
	 *            the element to search for
	 * @return true if the element has been added to the hierarchy, false
	 *         otherwise
	 */
	@Override
	public boolean has(Capitalist capitalist) {
		if (corpMap.containsKey(capitalist)) {
			return true;
		} else
			return false;
	}

	/**
	 * @return all elements in the hierarchy, or an empty set if no elements
	 *         have been added to the hierarchy
	 */
	@Override
	public Set<Capitalist> getElements() {
		return corpMap.keySet();
	}

	/**
	 * @return all parent elements in the hierarchy, or an empty set if no
	 *         parents have been added to the hierarchy
	 */
	@Override
	public Set<FatCat> getParents() {
		Set<FatCat> parents = (Set<FatCat>) corpMap.values();
		return parents;
	}

	/**
	 * @param fatCat
	 *            the parent whose children need to be returned
	 * @return all elements in the hierarchy that have the given parent as a
	 *         direct parent, or an empty set if the parent is not present in
	 *         the hierarchy or if there are no children for the given parent
	 */
	@Override
	public Set<Capitalist> getChildren(FatCat fatCat) {
		throw new NotImplementedException();
	}

	/**
	 * @return a map in which the keys represent the parent elements in the
	 *         hierarchy, and the each value is a set of the direct children of
	 *         the associate parent, or an empty map if the hierarchy is empty.
	 */
	@Override
	public Map<FatCat, Set<Capitalist>> getHierarchy() {
		throw new NotImplementedException();
	}

	/**
	 * @param capitalist
	 * @return the parent chain of the given element, starting with its direct
	 *         parent, then its parent's parent, etc, or an empty list if the
	 *         given element has no parent or if its parent is not in the
	 *         hierarchy
	 */
	@Override
	public List<FatCat> getParentChain(Capitalist capitalist) {
		ArrayList<FatCat> fCatList = new ArrayList<>();
		if (capitalist.hasParent() == true) {
			fCatList.add(capitalist.getParent());

			for (Capitalist cap : fCatList) {
				if (cap.hasParent()) {
					fCatList.add(cap.getParent());
				}
			}
		}
		return fCatList;
	}
}
