package Util;

/**
 * Created by Administrator on 2017-9-27.
 */
public class GetQuerySql {

	public String getZongSql(String progress , String date){
		String sql = null;
		if(progress.equals("tiao")){
			sql = "select basicmodel,sum(shi) as tiaoshi\n" +
					"from (SELECT m1.basicmodel,pd1.VIN,id,case when pd1.finalfact in('04','05','06','09','10','11','18') then 1 else 0 end as shi\n" +
					"FROM PRODPLAN Pd1\n" +
					"join modelinfo m1 on m1.model = pd1.model\n" +
					"WHERE pd1.LINENO IN ('ZZ1','ZZ2','ZZ3','DZ1','ZZX','1E','EZ1','EZ2','EZ3','XZZ','RT','GC0','EJ1','EJ3')\n" +
					"AND pd1.finalfact <> '37' and m1.basicmodel is not null\n" +
					"AND pd1.onmark = '*' AND substr(pd1.finalfacttime, 0, 8) = '"+date+"')\n" +
					"group by basicmodel\n" +
					"having sum(shi) > 0\n" +
					"order by tiaoshi DESC";
		}else if(progress.equals("tiaocount")){
			sql = "select count(*) tiaocount\n" +
					"from (SELECT m1.basicmodel,pd1.VIN,id\n" +
					"FROM PRODPLAN Pd1\n" +
					"join modelinfo m1 on m1.model = pd1.model\n" +
					"WHERE pd1.LINENO IN ('ZZ1','ZZ2','ZZ3','DZ1','ZZX','1E','EZ1','EZ2','EZ3','XZZ','RT','GC0','EJ1','EJ3')\n" +
					"AND pd1.finalfact in('04','05','06','09','10','11','18')\n" +
					"AND pd1.finalfact <> '37' and m1.basicmodel is not null\n" +
					"AND pd1.onmark = '*' AND substr(pd1.finalfacttime, 0, 8) = '"+date+"')";
		}else if(progress.equals("pen")){
			sql = "select basicmodel,sum(wan) as wan\n" +
					"from (SELECT m1.basicmodel,pd1.VIN,id,case when pd1.finalfact in ('21','24','25','26','27','30','31','32','35','36') then 1 else 0 end as wan\n" +
					"FROM PRODPLAN Pd1\n" +
					"join modelinfo m1 on m1.model = pd1.model\n" +
					"WHERE pd1.LINENO IN ('ZZ1','ZZ2','ZZ3','DZ1','ZZX','1E','EZ1','EZ2','EZ3','XZZ','RT','GC0','EJ1','EJ3')\n" +
					"AND pd1.finalfact <> '37' and m1.basicmodel is not null\n" +
					"AND pd1.onmark = '*' AND substr(pd1.finalfacttime, 0, 8) = '"+date+"')\n" +
					"group by basicmodel\n" +
					"having sum(wan) > 0\n" +
					"order by wan DESC";
		}else if(progress.equals("pencount")){
			sql = "select count(*) as pencount\n" +
					"from (SELECT m1.basicmodel,pd1.VIN,id\n" +
					"FROM PRODPLAN Pd1\n" +
					"join modelinfo m1 on m1.model = pd1.model\n" +
					"where pd1.finalfact in ('21','24','25','26','27','30','31','32','35','36')\n" +
					"AND pd1.LINENO IN ('ZZ1','ZZ2','ZZ3','DZ1','ZZX','1E','EZ1','EZ2','EZ3','XZZ','RT','GC0','EJ1','EJ3')\n" +
					"AND pd1.finalfact <> '37' and m1.basicmodel is not null\n" +
					"AND pd1.onmark = '*' AND substr(pd1.finalfacttime, 0, 8) = '"+date+"')";
		}else if(progress.equals("dayin")){
			sql = "SELECT BASICMODEL,sum(dayin) dayin\n" +
					"FROM (Select ML1.BASICMODEL,VIN,case when substr(finalfacttime, 0, 8) = '"+date+"' then 1 else 0 end as dayin\n" +
					"from prodplan PD4 LEFT JOIN MODELINFO ML1 ON PD4.MODEL = ML1.MODEL\n" +
					"where PD4.LINENO IN ('ZZ1','ZZ2','ZZ3','DZ1','ZZX','1E','EZ1','EZ2','EZ3','XZZ','RT','GC0','EJ1', 'EJ3')\n" +
					"AND PD4.Finalfact in ('37', '40') and substr(pd4.finalfacttime, 0, 4) = substr('"+date+"', 0, 4)\n" +
					"AND substr(pd4.finalfacttime, 0, 6) <= substr('"+date+"', 0, 6)) MP2\n" +
					"GROUP BY BASICMODEL\n" +
					"having sum(dayin) > 0\n" +
					"order by dayin DESC";
		}else if(progress.equals("incount")){
			sql ="SELECT count(*) as incount\n" +
					"FROM (Select ML1.BASICMODEL,VIN\n" +
					"from prodplan PD4 LEFT JOIN MODELINFO ML1 ON PD4.MODEL = ML1.MODEL\n" +
					"where PD4.LINENO IN ('ZZ1','ZZ2','ZZ3','DZ1','ZZX','1E','EZ1','EZ2','EZ3','XZZ','RT','GC0','EJ1', 'EJ3')\n" +
					"AND PD4.Finalfact in ('37', '40') and substr(finalfacttime, 0, 8) = '"+date+"')";
		}else if(progress.equals("dayout")){
			sql = "SELECT BASICMODEL,sum(dayout) dayout\n" +
					"FROM (Select ML1.BASICMODEL,VIN,case when substr(OFFTIME, 0, 8) = '"+date+"' then 1 else 0 end as dayout\n" +
					"from prodplan PD4 LEFT JOIN MODELINFO ML1 ON PD4.MODEL = ML1.MODEL\n" +
					"where PD4.LINENO IN ('ZZ1','ZZ2','ZZ3','DZ1','ZZX','1E','EZ1','EZ2','EZ3','XZZ', 'RT','GC0', 'EJ1','EJ3')\n" +
					"AND ML1.BASICMODEL is not null AND substr(OFFTIME, 0, 4) = substr('"+date+"', 0, 4)\n" +
					"AND OFFMARK = '*' AND substr(offtime, 0, 6) <= '"+date+"') MP\n" +
					"GROUP BY BASICMODEL\n" +
					"having sum(dayout) >0 \n" +
					"order by dayout DESC";
		}else if(progress.equals("outcount")){
			sql = "SELECT count(*) as outcount\n" +
					"FROM (Select ML1.BASICMODEL,VIN\n" +
					"from prodplan PD4 LEFT JOIN MODELINFO ML1 ON PD4.MODEL = ML1.MODEL\n" +
					"where PD4.LINENO IN ('ZZ1','ZZ2','ZZ3','DZ1','ZZX','1E','EZ1','EZ2','EZ3','XZZ', 'RT','GC0', 'EJ1','EJ3')\n" +
					"AND ML1.BASICMODEL is not null AND substr(OFFTIME, 0, 8) = '"+date+"' AND OFFMARK = '*')";
		}else if(progress.equals("monthout")){
			sql = "SELECT sum(monthout) monthout\n" +
					"FROM (Select ML1.BASICMODEL, VIN,case when substr(OFFTIME, 0, 6) = substr('"+date+"', 0, 6) then 1 else 0 end as monthout\n" +
					"from prodplan PD4 LEFT JOIN MODELINFO ML1 ON PD4.MODEL = ML1.MODEL\n" +
					"where PD4.LINENO IN ('ZZ1','ZZ2','ZZ3','DZ1','ZZX','1E','EZ1','EZ2','EZ3', 'XZZ','RT','GC0','EJ1','EJ3')\n" +
					"AND substr(OFFTIME, 0, 4) = substr('"+date+"', 0, 4)\n" +
					"AND OFFMARK = '*' AND substr(offtime, 0, 6) <= '"+date+"')";
		}else if(progress.equals("monthdayout")){
			sql = "SELECT dd,count(*) ddout\n" +
					"FROM (Select ML1.BASICMODEL, VIN, to_number(substr(offtime, 7, 2)) as dd\n" +
					"from prodplan PD4 LEFT JOIN MODELINFO ML1 ON PD4.MODEL = ML1.MODEL\n" +
					"where PD4.LINENO IN ('ZZ1','ZZ2','ZZ3','DZ1','ZZX','1E','EZ1','EZ2','EZ3', 'XZZ','RT','GC0','EJ1','EJ3')\n" +
					"AND OFFMARK = '*' AND substr(offtime, 0, 6) = substr('"+date+"', 0, 6) and ML1.BASICMODEL is not null) \n" +
					"group by dd\n" +
					"order by dd";
		}else if(progress.equals("monthin")){
			sql = "SELECT sum(monthin) monthin\n" +
					"FROM (Select ML1.BASICMODEL,VIN,case when substr(finalfacttime, 0, 6) = substr('"+date+"', 0, 6) then 1 else 0 end as monthin\n" +
					"from prodplan PD4 LEFT JOIN MODELINFO ML1 ON PD4.MODEL = ML1.MODEL\n" +
					"where PD4.LINENO IN ('ZZ1','ZZ2','ZZ3','DZ1','ZZX','1E','EZ1','EZ2','EZ3','XZZ','RT','GC0','EJ1','EJ3')\n" +
					"AND PD4.Finalfact in ('37', '40') and substr(pd4.finalfacttime, 0, 4) = substr('"+date+"', 0, 4)\n" +
					"AND substr(pd4.finalfacttime, 0, 6) <= substr('"+date+"', 0, 6))";
		}else if(progress.equals("montdayhin")){
			sql = "SELECT dd ,count(*) ddin\n" +
					"FROM (Select ML1.BASICMODEL,VIN,to_number(substr(pd4.finalfacttime, 7, 2)) as dd\n" +
					"from prodplan PD4 LEFT JOIN MODELINFO ML1 ON PD4.MODEL = ML1.MODEL\n" +
					"where PD4.LINENO IN ('ZZ1','ZZ2','ZZ3','DZ1','ZZX','1E','EZ1','EZ2','EZ3','XZZ','RT','GC0','EJ1','EJ3') and ML1.BASICMODEL is not null\n" +
					"AND PD4.Finalfact in ('37', '40') AND substr(pd4.finalfacttime, 0, 6) = substr('"+date+"', 0, 6))\n" +
					"group by dd\n" +
					"order by dd";
		}
		return sql;
	}

	public String getDiPanSql(String progress , String date){
		String sql = null;
		if(progress.equals("tiao")){
			sql = "select basicmodel,sum(shi) as tiaoshi\n" +
					"from (SELECT m1.basicmodel,pd1.VIN,id,case when pd1.finalfact in ('04','05','06','09','10','11','18','35','37') then 1 else 0 end as shi\n" +
					"FROM PRODPLAN Pd1 join modelinfo m1 on m1.model = pd1.model\n" +
					"WHERE pd1.LINENO IN ('DP0','DP1','DP2','DP3','DP5','1D','DD1','DD2','DD3','GC1')\n" +
					"AND pd1.finalfact <> '37' and m1.basicmodel is not null\n" +
					"and pd1.onmark = '*' AND substr(pd1.finalfacttime, 0, 8) = '"+date+"')\n" +
					"group by basicmodel\n" +
					"having sum(shi) > 0\n" +
					"order by sum(shi) DESC";
		}else if(progress.equals("tiaocount")){
			sql = "select count(*) tiaocount\n" +
					"from (SELECT m1.basicmodel,pd1.VIN,id\n" +
					"FROM PRODPLAN Pd1 join modelinfo m1 on m1.model = pd1.model\n" +
					"WHERE pd1.LINENO IN ('DP0','DP1','DP2','DP3','DP5','1D','DD1','DD2','DD3','GC1')\n" +
					"AND pd1.finalfact in ('04','05','06','09','10','11','18','35','37')\n" +
					"AND pd1.finalfact <> '37' and m1.basicmodel is not null\n" +
					"and pd1.onmark = '*' AND substr(pd1.finalfacttime, 0, 8) = '"+date+"')";
		}else if(progress.equals("dayin")){
			sql = "SELECT BASICMODEL,sum(dayin) dayin\n" +
					"FROM (Select ML1.BASICMODEL,VIN,case when substr(finalfacttime, 0, 8) = '"+date+"' then 1 else 0 end as dayin\n" +
					"from prodplan PD4 LEFT JOIN MODELINFO ML1 ON PD4.MODEL = ML1.MODEL\n" +
					"where PD4.LINENO IN ('DP0','DP1', 'DP2','DP3','DP5','1D','DD1', 'DD2','DD3','GC1')\n" +
					"AND PD4.Finalfact = '37' and substr(pd4.finalfacttime, 0, 4) = substr('"+date+"', 0, 4)\n" +
					"AND substr(pd4.finalfacttime, 0, 6) <= substr('"+date+"', 0, 6)) MP2\n" +
					"GROUP BY BASICMODEL\n" +
					"having sum(dayin) > 0\n" +
					"order by dayin DESC";
		}else if(progress.equals("incount")){
			sql ="SELECT count(*) as incount\n" +
					"FROM (Select ML1.BASICMODEL,VIN\n" +
					"from prodplan PD4 LEFT JOIN MODELINFO ML1 ON PD4.MODEL = ML1.MODEL\n" +
					"where PD4.LINENO IN ('DP0','DP1', 'DP2','DP3','DP5','1D','DD1', 'DD2','DD3','GC1')\n" +
					"AND PD4.Finalfact = '37' and ML1.BASICMODEL is not null\n" +
					"AND substr(finalfacttime, 0, 8) = '"+date+"')";
		}else if(progress.equals("dayout")){
			sql = "SELECT BASICMODEL,sum(dayout) dayout\n" +
					"FROM (Select ML1.BASICMODEL,VIN,case when substr(OFFTIME, 0, 8) = '"+date+"' then 1 else 0 end as dayout\n" +
					"from prodplan PD4 LEFT JOIN MODELINFO ML1 ON PD4.MODEL = ML1.MODEL\n" +
					"where PD4.LINENO IN ('DP0','DP1','DP2','DP3','DP5','1D','DD1', 'DD2','DD3','GC1')\n" +
					"AND substr(OFFTIME, 0, 4) = substr('"+date+"', 0, 4) AND ML1.BASICMODEL is not null\n" +
					"AND OFFMARK = '*' AND substr(offtime, 0, 6) <= '"+date+"') MP\n" +
					"GROUP BY BASICMODEL\n" +
					"having sum(dayout) >0 \n" +
					"order by dayout DESC";
		}else if(progress.equals("outcount")){
			sql = "SELECT count(*) as outcount\n" +
					"FROM (Select ML1.BASICMODEL,VIN\n" +
					"from prodplan PD4 LEFT JOIN MODELINFO ML1 ON PD4.MODEL = ML1.MODEL\n" +
					"where PD4.LINENO IN ('DP0','DP1','DP2','DP3','DP5','1D','DD1', 'DD2','DD3','GC1')\n" +
					"AND ML1.BASICMODEL is not null AND OFFMARK = '*' AND substr(OFFTIME, 0, 8) = '"+date+"')";
		}else if(progress.equals("monthout")){
			sql = "SELECT sum(monthout) monthout\n" +
					"FROM (Select ML1.BASICMODEL,VIN,case when substr(OFFTIME, 0, 6) = substr('"+date+"', 0, 6) then 1 else 0end as monthout\n" +
					"from prodplan PD4 LEFT JOIN MODELINFO ML1 ON PD4.MODEL = ML1.MODEL\n" +
					"where PD4.LINENO IN ('DP0','DP1','DP2','DP3','DP5','1D','DD1','DD2','DD3','GC1')\n" +
					"AND substr(OFFTIME, 0, 4) = substr('"+date+"', 0, 4)\n" +
					"AND OFFMARK = '*' AND substr(offtime, 0, 6) <= '"+date+"')";
		}else if(progress.equals("monthdayout")){
			sql = "SELECT dd,count(*) ddout\n" +
					"FROM (Select ML1.BASICMODEL,VIN, to_number(substr(offtime, 7, 2)) as dd\n" +
					"from prodplan PD4 LEFT JOIN MODELINFO ML1 ON PD4.MODEL = ML1.MODEL\n" +
					"where PD4.LINENO IN ('DP0','DP1','DP2','DP3','DP5','1D','DD1','DD2','DD3','GC1')\n" +
					"AND OFFMARK = '*' AND substr(OFFTIME, 0, 6) = substr('"+date+"', 0, 6) and ML1.BASICMODEL is not null)\n" +
					"group by dd\n" +
					"order by dd";
		}else if(progress.equals("monthin")){
			sql = "SELECT sum(monthin) monthin\n" +
					"FROM (Select ML1.BASICMODEL,VIN,case when substr(finalfacttime, 0, 6) = substr('"+date+"', 0, 6) then 1 else 0 end as monthin\n" +
					"from prodplan PD4 LEFT JOIN MODELINFO ML1 ON PD4.MODEL = ML1.MODEL\n" +
					"where PD4.LINENO IN ('DP0','DP1','DP2','DP3','DP5','1D','DD1','DD2','DD3','GC1')\n" +
					"AND PD4.Finalfact = '37' and substr(pd4.finalfacttime, 0, 4) =  substr('"+date+"', 0, 4)\n" +
					"AND substr(pd4.finalfacttime, 0, 6) <= substr('"+date+"', 0, 6))";
		}else if(progress.equals("montdayhin")){
			sql = "SELECT dd,count(*) ddin\n" +
					"FROM (Select ML1.BASICMODEL,VIN,to_number(substr(finalfacttime, 7, 2)) as dd\n" +
					"from prodplan PD4 LEFT JOIN MODELINFO ML1 ON PD4.MODEL = ML1.MODEL\n" +
					"where PD4.LINENO IN ('DP0','DP1','DP2','DP3','DP5','1D','DD1','DD2','DD3','GC1')\n" +
					"AND PD4.Finalfact = '37' and ML1.BASICMODEL is not null\n" +
					"AND substr(finalfacttime, 0, 6) = substr('"+date+"', 0, 6))\n" +
					"group by dd\n" +
					"order by dd";
		}
		return sql;
	}

}
