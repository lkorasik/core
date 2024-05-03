export interface Props {
    checked?: boolean
    onChange: (e: boolean) => void
}

export function CheckBox(props: Props) {
    return <input 
        type="checkbox" 
        defaultChecked={props.checked} 
        onChange={(e) => props.onChange(e.target.checked)}/>
}