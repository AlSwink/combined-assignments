package com.cooksys.ftd.assignments.collections;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.cooksys.ftd.assignments.collections.hierarchy.Hierarchy;
import com.cooksys.ftd.assignments.collections.model.Capitalist;
import com.cooksys.ftd.assignments.collections.model.FatCat;
import com.cooksys.ftd.assignments.collections.model.WageSlave;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class MegaCorp implements Hierarchy<Capitalist, FatCat> {

	// try changing this to a HashSet.
	public HashSet<Capitalist> hierarchy = new HashSet<>();

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
		if (capitalist == null)
			return false;

		// if capitalist is already present do not add it and return false
		if (has(capitalist) == false) {

			if (capitalist.hasParent()) {
				if (has(capitalist.getParent()) == false) {
					add(capitalist.getParent());
				}
				hierarchy.add(capitalist);
				return true;
				

			} else if (capitalist instanceof WageSlave)
				return false;
			else {
				hierarchy.add(capitalist);
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
		if (hierarchy.contains(capitalist)) {
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
		HashSet<Capitalist> eSet = new HashSet<>();
		eSet.addAll(hierarchy);
		return eSet;
	}

	/**
	 * @return all parent elements in the hierarchy, or an empty set if no
	 *         parents have been added to the hierarchy
	 */
	@Override
	public Set<FatCat> getParents() {
		HashSet<FatCat> parents = new HashSet<>();
		for (Capitalist cap : hierarchy) {
			if (cap instanceof FatCat)
				parents.add((FatCat) cap);
		}
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
		HashSet<Capitalist> children = new HashSet<>();
		for (Capitalist child : hierarchy) {
			if (child.hasParent() && child.getParent() == fatCat) {
				children.add(child);
			}
		}
		return children;
	}

	/**
	 * @return a map in which the keys represent the parent elements in the
	 *         hierarchy, and the each value is a set of the direct children of
	 *         the associate parent, or an empty map if the hierarchy is empty.
	 */
	@Override
	public Map<FatCat, Set<Capitalist>> getHierarchy() {
		HashMap<FatCat, Set<Capitalist>> defHier = new HashMap<>();
		 Set<FatCat> parents = getParents();
		 Set<Capitalist> children;
		 for(FatCat fCat : parents){
		 children = getChildren(fCat);
		 defHier.put(fCat, children);
		// children = null;
		 }
		return defHier;
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
		ArrayList<FatCat> pChain = new ArrayList<>();
		if (capitalist != null) {
			while (capitalist.hasParent() && has(capitalist.getParent())) {
				pChain.add(capitalist.getParent());
				capitalist = capitalist.getParent();
			}
		}

		return pChain;
	}
}
