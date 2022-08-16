import SunEditor from 'suneditor-react';
import {align, fontSize, formatBlock, horizontalRule, list, table} from "suneditor/src/plugins";
import 'suneditor/dist/css/suneditor.min.css';
import "./Editor.css";

const Editor = ({
                    height = 200,
                    placeholder = "",
                    value = "",
                    onChange = () => {
                    }
                }) => {
    return (
        <div className="Editor s-vflex">
            <label>{placeholder}</label>
            <SunEditor
                height={height}
                placeholder={placeholder}
                lang="ua"
                defaultValue={value}
                onChange={onChange}
                setOptions={{
                    defaultTag: "div",
                    plugins: [
                        table,
                        fontSize,
                        formatBlock,
                        horizontalRule,
                        align,
                        list
                    ],
                    buttonList: [
                        ['undo', 'redo', 'removeFormat'],
                        ['fontSize', 'formatBlock'],
                        [
                            'bold',
                            'underline',
                            'italic',
                            'strike',
                            'subscript',
                            'superscript',
                        ],
                        ['align', 'horizontalRule', 'list', 'table']
                    ]
                }}
            />
        </div>
    );
}

export default Editor;
