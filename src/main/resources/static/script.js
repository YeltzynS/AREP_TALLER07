let currentUserId = null;
const SHARED_POST_STREAM_ID = 1; // ID del PostStream compartido

// Registrar un nuevo usuario
document.getElementById('registerForm').addEventListener('submit', function(event) {
    event.preventDefault();
    const name = document.getElementById('registerName').value;
    const email = document.getElementById('registerEmail').value;
    const password = document.getElementById('registerPassword').value;

    // Datos del usuario
    const requestData = {
        name: name,
        email: email,
        password: password
    };

    // Enviar la solicitud al backend
    fetch('http://localhost:8080/users', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(requestData)
    })
    .then(response => {
        if (!response.ok) {
            return response.json().then(err => { throw new Error(err.message || 'Error al registrar el usuario'); });
        }
        return response.json();
    })
    .then(data => {
        console.log('Usuario registrado:', data);
        alert('Usuario registrado con éxito.');
        document.getElementById('registerForm').reset();
        loadUsers(); // Recargar la lista de usuarios
    })
    .catch(error => {
        console.error('Error:', error);
        alert(error.message);
    });
});

// Crear un nuevo post
document.getElementById('postForm').addEventListener('submit', function(event) {
    event.preventDefault();
    const postContent = document.getElementById('postContent').value;
    const selectedUserId = document.getElementById('userSelect').value;

    if (!selectedUserId) {
        alert('Por favor, selecciona un usuario.');
        return;
    }

    const requestData = {
        content: postContent,
        user: { id: selectedUserId },
        postStream: { id: SHARED_POST_STREAM_ID } // Usar el PostStream compartido
    };

    fetch('http://localhost:8080/posts', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(requestData)
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Error al crear el post');
        }
        return response.json();
    })
    .then(data => {
        console.log('Post creado:', data);
        alert('Post creado con éxito.');
        document.getElementById('postForm').reset();
        loadPosts(); // Recargar los posts
    })
    .catch(error => {
        console.error('Error:', error);
        alert('Hubo un error al crear el post. Por favor, inténtalo de nuevo.');
    });
});

// Cargar y mostrar los usuarios
function loadUsers() {
    fetch('http://localhost:8080/users')
    .then(response => {
        if (!response.ok) {
            throw new Error('Error al cargar los usuarios');
        }
        return response.json();
    })
    .then(data => {
        const usersDiv = document.getElementById('users');
        const userSelect = document.getElementById('userSelect');

        // Limpiar el contenedor de usuarios y el select
        usersDiv.innerHTML = '<h3>Usuarios Registrados</h3>';
        userSelect.innerHTML = '<option value="">Selecciona un usuario</option>';

        // Mostrar la lista de usuarios
        data.forEach(user => {
            const userElement = document.createElement('div');
            userElement.textContent = `Nombre: ${user.name}, Email: ${user.email}`;
            usersDiv.appendChild(userElement);

            // Agregar el usuario al select
            const option = document.createElement('option');
            option.value = user.id;
            option.textContent = user.name;
            userSelect.appendChild(option);
        });
    })
    .catch(error => {
        console.error('Error:', error);
        alert('Hubo un error al cargar los usuarios. Por favor, inténtalo de nuevo.');
    });
}

// Cargar y mostrar los posts
function loadPosts() {
    fetch(`http://localhost:8080/posts/stream/${SHARED_POST_STREAM_ID}`)
    .then(response => {
        if (!response.ok) {
            throw new Error('Error al cargar los posts');
        }
        return response.json();
    })
    .then(data => {
        const postsDiv = document.getElementById('posts');
        postsDiv.innerHTML = '<h3>Posts</h3>'; // Limpiar y agregar título

        data.forEach(post => {
            const postElement = document.createElement('div');
            postElement.textContent = `${post.user.name}: ${post.content}`;
            postsDiv.appendChild(postElement);
        });
    })
    .catch(error => {
        console.error('Error:', error);
        alert('Hubo un error al cargar los posts. Por favor, inténtalo de nuevo.');
    });
}

// Cargar usuarios y posts al iniciar la página
loadUsers();
loadPosts();