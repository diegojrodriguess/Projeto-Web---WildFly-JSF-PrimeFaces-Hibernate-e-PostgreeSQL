package project001;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;
import javax.enterprise.context.ApplicationScoped;


@Named
@ApplicationScoped
public class CursoConverter implements Converter {

	@Inject
	private CursoDAO cursoDAO;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		try {
			if (value != null && !value.isEmpty()) {
				Curso curso = cursoDAO.findBySigla(value);
				if (curso == null) {
					throw new IllegalArgumentException("Curso não encontrado para a sigla: " + value);
				}
				return curso;
			}
			return null;
		} catch (Exception e) {
			throw new IllegalArgumentException("Erro ao converter para objeto", e);
		}
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		try {
            if (value instanceof Curso) {
                String sigla = ((Curso) value).getSigla();
                if (sigla != null) {
                    return sigla;
                } else {
                    throw new IllegalArgumentException("A sigla do curso é nula");
                }
            }
            return null;
        } catch (Exception e) {
            throw new IllegalArgumentException("Erro ao converter para String", e);
        }
    }
}
