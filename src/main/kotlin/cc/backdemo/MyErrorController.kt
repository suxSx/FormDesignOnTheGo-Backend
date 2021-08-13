package cc.backdemo

import org.springframework.boot.web.servlet.error.ErrorController
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import javax.servlet.RequestDispatcher
import javax.servlet.http.HttpServletRequest

@Controller
class MyErrorController : ErrorController {
    @RequestMapping("/error")
    fun handleError(request: HttpServletRequest): String {
        /* Get the error status */
        val status: Any = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        /* If not null display, if null return unknown error */
        val statusCode: String = status.toString();
        if(statusCode == HttpStatus.NOT_FOUND.value().toString()) {
            return "404-error"
        }

        return "error"
    }
}