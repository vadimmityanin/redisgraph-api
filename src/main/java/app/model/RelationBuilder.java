package app.model;

import app.model.relation.DomainRelation;
import app.model.relation.RelationType;
import com.querydsl.core.types.dsl.EntityPathBase;
import iot.jcypher.query.api.pattern.Node;
import iot.jcypher.query.api.pattern.Relation;
import iot.jcypher.query.values.JcRelation;

import java.util.Objects;

import static app.model.relation.RelationType.IN;
import static app.model.relation.RelationType.OUT;
import static java.util.Optional.ofNullable;

public class RelationBuilder {

    private Node node;

    public RelationBuilder(Node node) {
        this.node = node;
    }


    public NodeBuilder withRelation(JcRelation relation, RelationType type, DomainRelation relationName) {
        Objects.requireNonNull(node);
        Relation rel = node.relation(relation);
        applyType(type, rel);
        applyName(relationName, rel);
        return new NodeBuilder(rel);
    }

    private void applyName(DomainRelation relationName, Relation rel) {
        ofNullable(relationName).ifPresent(r -> rel.type(relationName.getValue()));
    }

    private void applyType(RelationType type, Relation rel) {
        if (OUT.equals(type)) {
            rel.out();
        } else if (IN.equals(type)) {
            rel.in();
        }
    }

    public Node build() {
        return node;
    }

}
