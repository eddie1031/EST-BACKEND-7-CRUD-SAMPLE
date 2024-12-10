function updateTaskStatus(code) {

    fetch(`${TASK_BASE}/${code}/status`, {
        method: "PATCH",
        headers : {
            "Content-Type": "application/json"
        },
        body: JSON.stringify("")
    })
    .then(resp => resp.json())
    .then( data => {
        if (data) {
            console.log(data.data);
            if (data.data.code === code) {
                alert(data.msg);
                location.reload();
            }
        }
    })
    .catch(
        err => {
            console.log(err);
        }
    )

}

function removeTask(code) {

    fetch(`${TASK_BASE}/${code}`, {
        method: "DELETE",
        headers : {
            "Content-Type": "application/json"
        },
        body: JSON.stringify("")
    })
    .then(resp => location.replace("/"))
    .catch(
        err => {
            console.log(err);
        }
    )

}

function askCheck(isCompleted, code) {

    const MSG = isCompleted
        ? "정말로 완료된 작업을 취소하시겠습니까?"
        : "정말로 작업을 완료하시겠습니까?";

    if ( confirm(MSG) ) {
        updateTaskStatus(code);
    }

}

function askRemove(code) {

    if ( confirm('정말로 작업을 삭제하시겠습니까? \n삭제된 작업은 복구할 수 없습니다.')) {
        removeTask(code);
    }

}

function loadMoreTask() {

    const pageBtnEl = document.getElementById('list-loading-btn');
    const pageNum = pageBtnEl.getAttribute("data-page");

    fetch(`tasks/more?page=${pageNum}`)
        .then(resp => resp.text())
        .then( htmlStr => {
            const htmlParser = new DOMParser();
            const parsedDocs = htmlParser.parseFromString(htmlStr, 'text/html');

            const tasksEl = parsedDocs.getElementById('loaded').innerHTML;

            document.getElementById('task-list-con').innerHTML += tasksEl;

            const hasNextEl = parsedDocs.getElementById('next-page');

            if ( !hasNextEl ) {
                pageBtnEl.style.display = 'none';
            } else {
                pageBtnEl.setAttribute('data-page', parseInt(pageNum) + 1);
            }

        })
        .catch(
            err => {
                console.log(err);
            }
        )
}
