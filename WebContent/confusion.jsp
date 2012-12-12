<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@ taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<!-- RichFaces tag library declaration -->
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>User intention</title>
<style type="text/css">
.colStyle1 {
	width: 150px;
	vertical-align: top;
}

.colStyle2 {
	width: 660px;
	height: 100%;
}

.rich-spinner-input-container {
	background-color: #CC2222;
}
</style>
</head>
<body>
	<f:view>
		<h:panelGrid columns="2" columnClasses="colStyle2, colStyle2"
			border="1">
			<h:form>
				<h:panelGrid columns="3" border="0"
					columnClasses="colStyle1, colStyle1, colStyle1">
					<h:outputText></h:outputText>
					<h:outputText>Predicted</h:outputText>
					<h:outputText></h:outputText>

					<h:outputText></h:outputText>
					<rich:inputNumberSpinner maxValue="100000"
						value="#{inputdata.dispMatrix.tn}" />
					<rich:inputNumberSpinner maxValue="100000"
						value="#{inputdata.dispMatrix.fp}" />

					<h:outputText>True</h:outputText>
					<h:outputText></h:outputText>
					<h:outputText></h:outputText>

					<h:outputText></h:outputText>
					<rich:inputNumberSpinner maxValue="100000"
						value="#{inputdata.dispMatrix.fn}" />
					<rich:inputNumberSpinner maxValue="100000"
						value="#{inputdata.dispMatrix.tp}" />
				</h:panelGrid>
				<br />
				<br />
				<h:commandButton value="Submit" action="#{inputdata.update}"
					style="font-size: 16px; horizontal-align: center;" />
			</h:form>
			<h:form id="configform">
				<h:panelGrid columns="2">
					<h:outputText>Initial Step size</h:outputText>
					<h:inputText id="step" value="#{inputdata.initialStepSize}">
						<a4j:support event="onblur" reRender="step" />
					</h:inputText>

					<h:outputText>Maximum Iterations</h:outputText>
					<h:inputText id="iter" value="#{inputdata.maxIterations}">
						<a4j:support event="onblur" reRender="iter" />
					</h:inputText>
				</h:panelGrid>
			</h:form>
		</h:panelGrid>
	</f:view>
</body>
</html>