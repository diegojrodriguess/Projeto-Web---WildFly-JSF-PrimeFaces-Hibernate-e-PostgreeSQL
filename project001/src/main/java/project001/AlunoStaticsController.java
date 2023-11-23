package project001;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.BarChartSeries;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;

@Named
@ViewScoped 
public class AlunoStaticsController implements Serializable {
	private static final long serialVersionUID = 1L;

	private BarChartModel barModel;

	@Inject
	private AlunoDAO alunoDAO;

	private List<Object[]> dadosGrafico;

	@PostConstruct
	public void init() {
		barModel = new BarChartModel();
		dadosGrafico = alunoDAO.contarAlunosPorCurso();

		if (dadosGrafico != null && !dadosGrafico.isEmpty()) {
			BarChartSeries series = new BarChartSeries();
			series.setLabel("Número de Alunos por Curso");

			// Itera sobre os dados obtidos do método contarAlunosPorCurso
			for (Object[] dados : dadosGrafico) {
				String cursoSigla = (String) dados[0];
				Long quantidadeAlunos = (Long) dados[1];
				

				// Adiciona os dados da série
				series.set(cursoSigla, quantidadeAlunos);
			}

			// Adiciona a série ao modelo do gráfico
			barModel.addSeries(series);

			// Configuração dos eixos do gráfico
			Axis xAxis = barModel.getAxis(AxisType.X);
			xAxis.setLabel("Cursos");

			Axis yAxis = barModel.getAxis(AxisType.Y);
			yAxis.setLabel("Número de Alunos");
			yAxis.setMin(0);
		}
	}

	public BarChartModel getBarModel() {
		return barModel;
	}
}
