package app.model;

import com.querydsl.core.types.dsl.EntityPathBase;
import iot.jcypher.query.api.pattern.Node;
import iot.jcypher.query.api.pattern.Relation;

public class NodeBuilder extends AbstractNodeCreator {

    private Relation relation;

    public NodeBuilder(Relation relation) {
        this.relation = relation;
    }

    public Relation build() {
        return relation;
    }

    public Relation getRelation() {
        return relation;
    }

    @Override
    public RelationBuilder node(NodeDefinition<? extends EntityPathBase> def, Object o) {
        return super.node(def, o);
    }

    @Override
    public RelationBuilder node(Node node, Object json) {
        return super.node(node, json);
    }


    @Override
    protected Node getNode(NodeDefinition<? extends EntityPathBase> def) {
        return relation.node(def);
    }
}
