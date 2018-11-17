package app.model;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.EntityPathBase;
import iot.jcypher.query.api.pattern.Relation;
import iot.jcypher.query.ast.pattern.PatternExpression;
import iot.jcypher.query.ast.pattern.PatternRelation;
import iot.jcypher.query.values.JcNode;
import iot.jcypher.query.values.JcProperty;
import iot.jcypher.query.values.JcRelation;

public class NodeDefinition<T extends EntityPathBase> extends JcNode {

    private Class<T> type;

    public NodeDefinition(String name, Class<T> type) {
        super(name);
        this.type = type;
    }


    public JcProperty property(Path path) {
        return this.property(path.getMetadata().getName());
    }

    public Class<T> getType() {
        return type;
    }
}
