import { AfterViewInit, Component, ElementRef, Input, OnDestroy, ViewChild, inject } from '@angular/core';
import { UiModuleLoaderService, UiRuntimeModule } from './ui-module-loader.service';

@Component({
  selector: 'app-ui-module-host',
  standalone: true,
  template: `<div #host class="runtime-module-host"></div>`
})
export class UiModuleHostComponent implements AfterViewInit, OnDestroy {
  private readonly loader = inject(UiModuleLoaderService);
  private mounted?: UiRuntimeModule;

  @Input({ required: true }) moduleId!: string;
  @Input() schoolSlug?: string;
  @Input() context: unknown;

  @ViewChild('host', { static: true }) private host!: ElementRef<HTMLElement>;

  async ngAfterViewInit(): Promise<void> {
    this.mounted = await this.loader.load(this.moduleId, this.schoolSlug);
    await this.mounted.mount(this.host.nativeElement, this.context);
  }

  async ngOnDestroy(): Promise<void> {
    if (this.mounted?.unmount) {
      await this.mounted.unmount(this.host.nativeElement);
    }
    this.host.nativeElement.replaceChildren();
  }
}
