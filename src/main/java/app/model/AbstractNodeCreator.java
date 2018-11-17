package app.model;

import com.querydsl.core.types.dsl.EntityPathBase;
import iot.jcypher.query.api.pattern.Node;
import org.apache.commons.beanutils.PropertyUtils;

import java.lang.reflect.InvocationTargetException;

import static java.util.Optional.ofNullable;

public abstract class AbstractNodeCreator {

    protected abstract Node getNode(NodeDefinition<? extends EntityPathBase> def);

    protected RelationBuilder node(NodeDefinition<? extends EntityPathBase> def, Object o) {
        Node n = getNode(def).label(def.getType().getSimpleName());
        ofNullable(o).ifPresent(body -> addBody(n, body));
        return new RelationBuilder(n);
    }

    protected RelationBuilder node(Node node, Object json) {
        ofNullable(json).ifPresent(body -> addBody(node, body));
        return new RelationBuilder(node);
    }

//    public <T extends NodeDefinition<? extends EntityPathBase>> RelationBuilder node(T nodeType, Object json) {
//        Node generatedNode = getNode().label(nodeType.getType().getSimpleName());
//        return node(generatedNode, json);
//    }

    private void addBody(Node n, Object o) {
        try {
            PropertyUtils.describe(o).entrySet().stream().filter(e -> !e.getKey().equals("class"))
                    .forEach((e) -> n.property(e.getKey()).value(e.getValue()));
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}
