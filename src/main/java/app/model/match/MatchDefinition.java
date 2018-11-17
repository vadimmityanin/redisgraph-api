package app.model.match;

import app.model.AbstractNodeCreator;
import app.model.NodeDefinition;
import app.model.RelationBuilder;
import com.querydsl.core.types.dsl.EntityPathBase;
import iot.jcypher.query.api.pattern.Node;
import iot.jcypher.query.factories.clause.MATCH;

public class MatchDefinition extends AbstractNodeCreator {

    @Override
    protected Node getNode(NodeDefinition<? extends EntityPathBase> def) {
        return MATCH.node(def);
    }

    public static MatchDefinition builder() {
        return new MatchDefinition();
    }

    @Override
    public RelationBuilder node(NodeDefinition<? extends EntityPathBase> def, Object o) {
        return super.node(def, o);
    }

    @Override
    public RelationBuilder node(Node node, Object json) {
        return super.node(node, json);
    }
}
