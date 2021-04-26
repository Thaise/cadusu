import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { environment } from '../environments/environment';

@Injectable()
export class BasicAuthInterceptor implements HttpInterceptor {

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        const ehApiUrl = request.url.startsWith(environment.API_URL);
        if (ehApiUrl) {
            request = request.clone({
                setHeaders: { 
                    Authorization: `Basic dXN1YXJpb3BmOnVzdSU0NSRSQQ==`
                }
            });
        }

        return next.handle(request);
    }
}