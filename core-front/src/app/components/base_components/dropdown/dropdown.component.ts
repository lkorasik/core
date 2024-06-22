import { Component, Input } from '@angular/core';

@Component({
    selector: 'app-dropdown',
    standalone: true,
    imports: [],
    templateUrl: './dropdown.component.html',
    styleUrl: './dropdown.component.css'
})
export class DropdownComponent {
    isActive: boolean = false;
    value: string = "Выберите элемент"
    @Input() values: DropdownItem[] = []
    @Input() defaultItemPosition: number = 0
    @Input() selected: ((item: DropdownItem) => void) | undefined

    ngOnInit() {
        this.value = this.values[this.defaultItemPosition].label
    }

    toggle() {
        this.isActive = !this.isActive
    }

    getClass() {
        if (this.isActive) {
            return 'is-active'
        } 
        return '';
    }

    onSelect(item: DropdownItem) {
        this.toggle()
        this.value = item.label
        if (this.selected !== undefined) {
            this.selected(item)
        }
    }

    rotate() {
        if (this.isActive) {
            return "up_arrow"
        }
        return ""
    }
}

export class DropdownItem {
    label: string
    value: string

    constructor(label: string, value: string) {
        this.label = label;
        this.value = value;
    }
}