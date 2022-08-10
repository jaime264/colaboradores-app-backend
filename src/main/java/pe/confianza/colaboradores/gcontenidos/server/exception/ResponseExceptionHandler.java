package pe.confianza.colaboradores.gcontenidos.server.exception;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

import pe.confianza.colaboradores.gcontenidos.server.bean.ResponseStatus;
import pe.confianza.colaboradores.gcontenidos.server.util.Constantes;

@ControllerAdvice
@RestController
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {
	
	private static final Logger log = LoggerFactory.getLogger(ResponseExceptionHandler.class);
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
		HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		String msgErr = "Validacion fallida";
		for (ObjectError objErr : ex.getBindingResult().getAllErrors()) {
			msgErr = objErr.getDefaultMessage();
		}
		ResponseStatus response = new ResponseStatus();
		response.setMsgStatus(msgErr);
		response.setCodeStatus(Constantes.COD_ERR);
		log.error("ERROR BAD_REQUEST: {}", msgErr);
		log.error(ex.getMessage(), ex);
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<ResponseStatus> manejarModeloExcepciones(Exception ex, WebRequest request) {
		ResponseStatus response = new ResponseStatus();
		response.setMsgStatus(ex.getMessage());
		response.setCodeStatus(Constantes.COD_ERR);
		log.error("ERROR INTERNAL_SERVER_ERROR {}", ex.getMessage());
		log.error(ex.getMessage(), ex);
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	


	@ExceptionHandler(AppException.class)
	public final ResponseEntity<ResponseStatus> manejarExcepciones(Exception ex, WebRequest request) {
		ResponseStatus response = new ResponseStatus();
		response.setMsgStatus(ex.getMessage());
		response.setCodeStatus(Constantes.COD_ERR);
		log.error("ERROR INTERNAL_SERVER_ERROR: {}", ex.getMessage());
		log.error(ex.getMessage(), ex);
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(ModelNotFoundException.class)
	public final ResponseEntity<ResponseStatus> manejarModeloExcepciones(ModelNotFoundException ex,
		WebRequest request) {
		ResponseStatus response = new ResponseStatus();
		response.setMsgStatus(ex.getMessage());
		response.setCodeStatus(Constantes.COD_EMPTY);
		log.error("ERROR NOT_FOUND: {}", ex.getMessage());
		log.error(ex.getMessage(), ex);
		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}
	
	public ResponseStatus getResponse(String msgError) {
		ObjectMapper objectMapper;
		ResponseStatus response;
		try {
			objectMapper = new ObjectMapper();
			return objectMapper.readValue(msgError, ResponseStatus.class);
		} catch (IOException e) {
			response = new ResponseStatus();
			response.setMsgStatus(msgError);
			return response;
		}
	}

}
