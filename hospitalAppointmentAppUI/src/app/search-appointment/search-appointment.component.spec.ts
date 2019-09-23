import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FormsModule} from '@angular/forms';

import { SearchAppointmentComponent } from './search-appointment.component';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';

describe('SearchAppointmentComponent', () => {
  let component: SearchAppointmentComponent;
  let fixture: ComponentFixture<SearchAppointmentComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SearchAppointmentComponent ],
      imports: [
        BrowserModule,
        FormsModule,
        HttpClientModule
      ],
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SearchAppointmentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it(`should have h2 as 'Search Appointment'`, async(() => {
    const compile = fixture.debugElement.nativeElement;
    const h2Atag = compile.querySelector('h2');
    expect(h2Atag.textContent).toEqual('Search Appointment');
  }));
});
