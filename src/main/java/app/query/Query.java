package app.query;

import iot.jcypher.database.internal.PlannerStrategy;
import iot.jcypher.domainquery.internal.Settings;
import iot.jcypher.query.JcQuery;
import iot.jcypher.query.api.IClause;
import iot.jcypher.query.writer.Format;
import iot.jcypher.util.Util;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Query {

    private JcQuery jcQuery = new JcQuery();
    private List<IClause> clauses = new LinkedList<>();

    public Query match(IClause match) {
        return addClause(match);
    }

    public Query create(IClause... create) {
        return addClause(create);
    }

    public Query where(IClause where) {
        return addClause(where);
    }

    public Query ret(IClause ret) {
        return addClause(ret);
    }

    public Query set(IClause set) {
        return addClause(set);
    }

    public Query addClause(IClause... clause) {
        clauses.addAll(Arrays.asList(clause));
        return this;
    }

    public String build() {
        Settings.plannerStrategy = PlannerStrategy.DEFAULT;
        jcQuery.setClauses(clauses.toArray(new IClause[0]));
        return Util.toCypher(jcQuery, Format.PRETTY_3);
    }

    public static Query builder() {
        return new Query();
    }

}
