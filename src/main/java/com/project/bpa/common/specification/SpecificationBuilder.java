package com.project.bpa.common.specification;

import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * Utility class for building dynamic specifications with chaining support.
 * This class provides a fluent API for creating complex specifications.
 *
 * @param <T> The entity type for which the specification is built
 */
public class SpecificationBuilder<T> {
    private final List<Specification<T>> specifications;

    private SpecificationBuilder() {
        this.specifications = new ArrayList<>();
    }

    /**
     * Creates a new instance of SpecificationBuilder.
     *
     * @param <T> The entity type
     * @return A new instance of SpecificationBuilder
     */
    public static <T> SpecificationBuilder<T> builder() {
        return new SpecificationBuilder<>();
    }

    /**
     * Adds an equal condition to the specification.
     *
     * @param field    The field name to compare
     * @param value    The value to compare against
     * @return The current SpecificationBuilder instance for method chaining
     */
    public SpecificationBuilder<T> equal(String field, Object value) {
        if (value != null) {
            specifications.add((root, query, criteriaBuilder) -> 
                criteriaBuilder.equal(root.get(field), value)
            );
        }
        return this;
    }

    /**
     * Adds a like condition to the specification (case-insensitive).
     *
     * @param field    The field name to compare
     * @param value    The value to search for
     * @return The current SpecificationBuilder instance for method chaining
     */
    public SpecificationBuilder<T> like(String field, String value) {
        if (value != null && !value.trim().isEmpty()) {
            specifications.add((root, query, criteriaBuilder) -> 
                criteriaBuilder.like(
                    criteriaBuilder.lower(root.get(field)),
                    "%" + value.toLowerCase() + "%"
                )
            );
        }
        return this;
    }

    /**
     * Adds a greater than or equal to condition to the specification.
     *
     * @param field    The field name to compare
     * @param value    The value to compare against
     * @param <Y>      The type of the value
     * @return The current SpecificationBuilder instance for method chaining
     */
    public <Y extends Comparable<? super Y>> SpecificationBuilder<T> greaterThanOrEqualTo(String field, Y value) {
        if (value != null) {
            specifications.add((root, query, criteriaBuilder) -> 
                criteriaBuilder.greaterThanOrEqualTo(root.get(field), value)
            );
        }
        return this;
    }

    /**
     * Adds a less than or equal to condition to the specification.
     *
     * @param field    The field name to compare
     * @param value    The value to compare against
     * @param <Y>      The type of the value
     * @return The current SpecificationBuilder instance for method chaining
     */
    public <Y extends Comparable<? super Y>> SpecificationBuilder<T> lessThanOrEqualTo(String field, Y value) {
        if (value != null) {
            specifications.add((root, query, criteriaBuilder) -> 
                criteriaBuilder.lessThanOrEqualTo(root.get(field), value)
            );
        }
        return this;
    }

    /**
     * Adds a custom specification to the builder.
     *
     * @param spec The specification to add
     * @return The current SpecificationBuilder instance for method chaining
     */
    public SpecificationBuilder<T> with(Specification<T> spec) {
        if (spec != null) {
            specifications.add(spec);
        }
        return this;
    }

    /**
     * Builds the final specification by combining all added conditions with AND operator.
     *
     * @return The combined specification
     */
    public Specification<T> build() {
        if (specifications.isEmpty()) {
            return (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();
        }

        Specification<T> result = specifications.get(0);
        for (int i = 1; i < specifications.size(); i++) {
            result = result.and(specifications.get(i));
        }
        return result;
    }
}
