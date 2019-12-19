// GET all
fetch('/users/').then(response => response.json().then(console.log))

// GET one user
fetch('/users/2').then(response => response.json().then(console.log))

// POST add new user
fetch(
    '/users/',
    {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ id: '6', userName: 'Alex_Fiest' })
    }
).then(result => result.json().then(console.log))

// PUT save existing
fetch(
    '/users/4',
    {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ id: 4, userName: 'Katy_Perry'})
    }
).then(result => result.json().then(console.log));

// DELETE existing
fetch('/users/4', { method: 'DELETE' }).then(result => console.log(result))

// GET all user phone book entry
fetch('/users/1/phonebook').then(response => response.json().then(console.log))

// GET one user phone book entry
fetch('/users/1/phonebook/2').then(response => response.json().then(console.log))

// POST add new user phone book entry
fetch(
    '/users/1',
    {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ title: 'Alex', number: '89119999999' })
    }
).then(result => result.json().then(console.log))

// PUT save existing phone book entry
fetch(
    '/users/1/phonebook/0',
    {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ title: 'Katy', number: '89117898778' })
    }
).then(result => result.json().then(console.log));

// DELETE existing phone book entry
fetch('/users/1/phonebook/0', { method: 'DELETE' }).then(result => console.log(result))