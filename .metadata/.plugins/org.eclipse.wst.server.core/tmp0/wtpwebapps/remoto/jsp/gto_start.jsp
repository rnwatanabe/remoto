<%@ page contentType="text/html;charset=ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>


<html xmlns:v="urn:schemas-microsoft-com:vml"
	xmlns:o="urn:schemas-microsoft-com:office:office"
	xmlns:w="urn:schemas-microsoft-com:office:word"
	xmlns:m="http://schemas.microsoft.com/office/2004/12/omml"
	xmlns="http://www.w3.org/TR/REC-html40">



<body bgcolor="#faffff" lang=PT-BR link="#006600" vlink="#006600"
	style='tab-interval: 35.4pt'>


<table border=1>

	<TR>
		<TD width=400 align="center"><b>GTO configuration</b><BR>
		</TD>
	</TR>

	<TR>
		<TD><BR>
		<b>&nbsp;Enter the number of muscle fibers (by fiber type)<BR>
		&nbsp;that insert into the GTO:<BR>
		<BR>
		</b>

		<FORM ACTION="/remoto/servlet/GTOStart" METHOD=POST>

		&nbsp;&nbsp;&nbsp;&nbsp;Slow (S): <input NAME="str_numSfibers" SIZE=6
			VALUE=5><br />
		&nbsp;&nbsp;&nbsp;&nbsp;Fast resistant (FR): <input
			NAME="str_numFRfibers" SIZE=6 VALUE=4><br />
		&nbsp;&nbsp;&nbsp;&nbsp;Fast fatigable (FF): <input
			NAME="str_numFFfibers" SIZE=6 VALUE=4><br />
		<br />

		<input TYPE="hidden" NAME="initial_time" VALUE=0.0> <input
			TYPE="hidden" NAME="time_step" VALUE=0.001> <input
			TYPE="hidden" NAME="final_time" VALUE=2.0> <input
			TYPE="hidden" NAME="start_time" VALUE=1.0> <input
			TYPE="hidden" NAME="end_time" VALUE=1.07> <input
			TYPE="hidden" NAME="constant_value" VALUE=0.01> <input
			TYPE="hidden" NAME="initial_value" VALUE=1.0> <input
			TYPE="hidden" NAME="final_value" VALUE=1.0112> <input
			TYPE="hidden" NAME="start_time_sin" VALUE=0.0> <input
			TYPE="hidden" NAME="end_time_sin" VALUE=2.0> <input
			TYPE="hidden" NAME="amplitude" VALUE=0.012> <input
			TYPE="hidden" NAME="frequency" VALUE=1> <input
			TYPE="hidden" NAME="phase" VALUE=90> <input TYPE="hidden"
			NAME="bias" VALUE=0.95> <input TYPE="hidden" NAME="isRandom"
			VALUE=""> <input TYPE="hidden" NAME="stddev" VALUE=0.001>
		<input TYPE="hidden" NAME="LPcutoff" VALUE=10.0> <input
			type="hidden" NAME="input_type" VALUE="constant"> <input
			type="hidden" NAME="index_input" VALUE="0">

		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		&nbsp;&nbsp;&nbsp;&nbsp; <input type="submit" value="Confirm" /></FORM>
		</TD>
	</TR>



</table>

</body>
</html>
