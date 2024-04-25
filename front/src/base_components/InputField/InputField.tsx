import { Flex } from "../Flex/Flex";
import { Input } from "../Input/Input";
import { NText } from "../NText/NText";

interface Props {
    type?: string
    placeholder?: string
    isRequired?: boolean
    onChange?: React.ChangeEventHandler<HTMLInputElement> | undefined
    children?: string
    value?: string
}

export function InputField(props: Props) {
    return (
        <Flex direction="column">
            <NText isRequired={props.isRequired}>{props.children}</NText>
            <Input type={props.type} placeholder={props.placeholder} isRequired={props.isRequired} onChange={props.onChange} value={props.value}/>
        </Flex>
    )
}