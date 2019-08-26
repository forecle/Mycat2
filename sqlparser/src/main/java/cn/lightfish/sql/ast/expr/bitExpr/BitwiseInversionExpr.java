package cn.lightfish.sql.ast.expr.bitExpr;

import cn.lightfish.sql.ast.RootExecutionContext;
import cn.lightfish.sql.ast.expr.numberExpr.LongExpr;
import cn.lightfish.sql.ast.expr.ValueExpr;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BitwiseInversionExpr implements LongExpr {

  private final RootExecutionContext context;
  private final ValueExpr value;

  @Override
  public Long getValue() {
    Long value = (Long) this.value.getValue();
    if (value == null){
      return null;
    }
    return ~value;
  }
}