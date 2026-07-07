import { Directive, ElementRef, Renderer2, DoCheck } from '@angular/core';

@Directive({
  selector: '[appNoTabByClass]',
  standalone: true
})
export class ProtectedFieldsDirective implements DoCheck {

  constructor(
    private el: ElementRef<HTMLElement>,
    private renderer: Renderer2
  ) {}

  ngDoCheck(): void {
    const isProtected = this.el.nativeElement.classList.contains('mapfield-prot');

    if (isProtected) {
      this.renderer.setAttribute(this.el.nativeElement, 'tabindex', '-1');
    } else {
      this.renderer.removeAttribute(this.el.nativeElement, 'tabindex');
    }
  }
}