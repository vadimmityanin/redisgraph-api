package app.model.create;

import app.model.AbstractNodeCreator;
import app.model.NodeDefinition;
import app.model.RelationBuilder;
import com.querydsl.core.types.dsl.EntityPathBase;
import iot.jcypher.query.api.pattern.Node;
import iot.jcypher.query.factories.clause.CREATE;

public class CreateDefinition extends AbstractNodeCreator {


    @Override
    protected Node getNode(NodeDefinition<? extends EntityPathBase> def) {
        return CREATE.node(def);
    }

    @Override
    public RelationBuilder node(NodeDefinition<? extends EntityPathBase> def, Object o) {
        return super.node(def, o);
    }

    public static CreateDefinition builder() {
        return new CreateDefinition();
    }
}
