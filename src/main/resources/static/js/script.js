document.getElementById("delete_input").disabled = true;
document.getElementById("delete_input").className = "minus_disabled";
const container = document.getElementById('input_container');
let countInput = 1;

//вызов ф-ции на добавление input
function addInput() {
    let input = document.createElement("input");
    input.id = 'value' + countInput;
    input.className = "input_value";
    input.type = "text";
    input.name = "medicine_name";
    input.placeholder = "Лекарство";
    input.required = true;
    countInput++;
    document.getElementById("delete_input").disabled = false;
    document.getElementById("delete_input").className = "plus_minus_btn";
    container.appendChild(input);
}

//вызов ф-ции на удаление input
function delInput() {
    let id = "value" + (countInput - 1);
    let node = document.getElementById(id);
    if (node.parentNode) {
        node.parentNode.removeChild(node);
    }
    if (countInput === 2) {
        document.getElementById("delete_input").disabled = true;
        document.getElementById("delete_input").className = "minus_disabled";
        countInput--;
    } else {
        countInput--;
    }
}



