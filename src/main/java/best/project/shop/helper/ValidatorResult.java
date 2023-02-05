package best.project.shop.helper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ValidatorResult {
	
	private boolean isValidationPass;
	private String error;
	
	public void setValidResult(){
		setValidationPass(Boolean.TRUE);
	}
	
	public void setNotValidResult(String error){
		setValidationPass(Boolean.FALSE);
		setError(error);
	}

}
