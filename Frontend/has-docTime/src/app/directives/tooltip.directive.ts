import { AfterViewInit, Directive, ElementRef, OnInit, Renderer2 } from '@angular/core';

@Directive({
  selector: '[appTooltip]'
})
export class TooltipDirective implements OnInit{

  constructor(private elementRef: ElementRef, private renderer: Renderer2) { }

  ngOnInit(): void {
    this.renderer.setAttribute(this.elementRef.nativeElement, 'data-toggle', 'tooltip');
    this.renderer.setAttribute(this.elementRef.nativeElement, 'data-placement', 'top');
    this.renderer.setAttribute(this.elementRef.nativeElement, 'title', "It should not contain whitespace characters. It must contain at least one uppercase character. It must contain at least one lowercase character. It must contain at least one special character. It must contain at least one number.");
    this.renderer.listen(this.elementRef.nativeElement, 'mouseenter', () => {
      this.renderer.addClass(this.elementRef.nativeElement, 'show');
    });
    this.renderer.listen(this.elementRef.nativeElement, 'mouseleave', () => {
      this.renderer.removeClass(this.elementRef.nativeElement, 'show');
    });
  }

}
