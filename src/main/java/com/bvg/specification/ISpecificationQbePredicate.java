package com.bvg.specification;

import com.querydsl.core.types.Predicate;

public interface ISpecificationQbePredicate<Q> {

    Predicate predicate(Q qbe);
}
