package com.example.myapplication 

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Navy = Color(0xFF0B2D4D)
private val Green = Color(0xFF32D583)
private val ScreenBackground = Color(0xFFF4F7F9)
private val GreyText = Color(0xFF777777)

// Data class required in the task
data class TeamTask(
    val id: Int,
    val title: String,
    val owner: String,
    val isDone: Boolean = false
)

@Composable
fun TeamTaskTracker() {

    // Four starting tasks
    // First two are completed
    val tasks = remember {
        mutableStateListOf(
            TeamTask(
                id = 1,
                title = "Create GitHub repository",
                owner = "Rahaf",
                isDone = true
            ),
            TeamTask(
                id = 2,
                title = "Write research summary",
                owner = "Omar",
                isDone = true
            ),
            TeamTask(
                id = 3,
                title = "Design the login screen",
                owner = "Noura"
            ),
            TeamTask(
                id = 4,
                title = "Set up Android Studio",
                owner = "Yousef"
            )
        )
    }

    // Derived values - NOT hard-coded
    val doneCount = tasks.count { it.isDone }
    val openCount = tasks.count { !it.isDone }
    val totalCount = tasks.size

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ScreenBackground)
    ) {

        // Top bar
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Navy)
                .padding(16.dp)
        ) {
            Text(
                text = "Team Task Tracker",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Task 11 - Challenge",
                color = Color.White,
                fontSize = 12.sp
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        ) {

            // Navy summary strip
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Navy
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {

                    SummaryItem(
                        number = doneCount,
                        label = "Done"
                    )

                    SummaryItem(
                        number = openCount,
                        label = "Open"
                    )

                    SummaryItem(
                        number = totalCount,
                        label = "Total"
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Task list
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                items(
                    items = tasks,
                    key = { it.id }
                ) { task ->

                    TaskRow(
                        task = task,
                        onToggle = {
                            val index = tasks.indexOfFirst {
                                it.id == task.id
                            }

                            if (index != -1) {
                                tasks[index] = task.copy(
                                    isDone = !task.isDone
                                )
                            }
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Add Task button
            Button(
                onClick = {

                    val nextId =
                        (tasks.maxOfOrNull { it.id } ?: 0) + 1

                    tasks.add(
                        TeamTask(
                            id = nextId,
                            title = "New task",
                            owner = "Fajr"
                        )
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Green,
                    contentColor = Navy
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "+   Add Task",
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}


// Reusable stateless composable
@Composable
fun TaskRow(
    task: TeamTask,
    onToggle: () -> Unit
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Checkbox(
                checked = task.isDone,
                onCheckedChange = {
                    onToggle()
                },
                colors = CheckboxDefaults.colors(
                    checkedColor = Green
                )
            )

            Spacer(modifier = Modifier.width(8.dp))

            Column {

                Text(
                    text = task.title,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = if (task.isDone) {
                        GreyText
                    } else {
                        Navy
                    },

                    // Line through completed tasks
                    textDecoration = if (task.isDone) {
                        TextDecoration.LineThrough
                    } else {
                        TextDecoration.None
                    }
                )

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = task.owner,
                    fontSize = 11.sp,
                    color = GreyText
                )
            }
        }
    }
}


// Summary item
@Composable
fun SummaryItem(
    number: Int,
    label: String
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = number.toString(),
            color = Green,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = label,
            color = Color.White,
            fontSize = 10.sp
        )
    }
}


@Preview(showBackground = true)
@Composable
fun TeamTaskTrackerPreview() {
    TeamTaskTracker()
}