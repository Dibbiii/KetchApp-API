### Da Fare per tutte le tabelle del DB
```
CREATE POLICY "Select own user" ON public.users
FOR SELECT
USING (id = (select auth.uid()));
```


- Tomatoes Fatto
- Friends Fatto
- Appointments Fatto
- Activities Fatto
- Achievements Fatto
- Users 